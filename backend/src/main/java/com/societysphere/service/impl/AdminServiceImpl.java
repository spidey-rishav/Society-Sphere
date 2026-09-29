package com.societysphere.service.impl;

import com.societysphere.dto.resident.CreateResidentRequest;
import com.societysphere.dto.resident.ResidentResponse;
import com.societysphere.dto.securityguard.CreateSecurityGuardRequest;
import com.societysphere.dto.securityguard.SecurityGuardResponse;
import com.societysphere.entity.*;
import com.societysphere.enums.AccountStatus;
import com.societysphere.enums.UserRole;
import com.societysphere.enums.FlatOccupancyStatus;
import com.societysphere.exception.BadRequestException;
import com.societysphere.repository.*;
import com.societysphere.response.ApiResponse;
import com.societysphere.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final ResidentRepository residentRepository;
    private final SecurityGuardRepository securityGuardRepository;
    private final FlatRepository flatRepository;
    private final SocietyRepository societyRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mail;

    @Value("${app.mail.enabled:false}") private boolean mailEnabled;
    @Value("${spring.mail.username:}") private String senderEmail;

    private String generateTempPassword() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    @Override
    @Transactional
    public ApiResponse<ResidentResponse> createResident(CreateResidentRequest request, Long societyId) {
        String email = request.getEmail().trim().toLowerCase();
        if (userRepository.existsByEmail(email)) {
            throw new BadRequestException("This email is already registered. Use a different email address for the resident.");
        }
        Flat flat = flatRepository.findById(request.getFlatId())
                .orElseThrow(() -> new RuntimeException("Flat not found"));

        if (!flat.getSociety().getId().equals(societyId)) {
            throw new RuntimeException("Flat does not belong to the given society");
        }

        String tempPassword = generateTempPassword();
        log.info("Temporary password for new resident {}: {}", request.getEmail(), tempPassword);

        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(tempPassword))
                .role(UserRole.RESIDENT)
                .accountStatus(AccountStatus.ACTIVE)
                .emailVerified(false)
                .firstLogin(true)
                .build();
        user = userRepository.save(user);

        Resident resident = Resident.builder()
                .user(user)
                .flat(flat)
                .fullName(request.getFullName())
                .mobileNumber(request.getMobileNumber())
                .gender(request.getGender())
                .residentType(request.getResidentType())
                .dateOfBirth(request.getDateOfBirth())
                .emergencyContactName(request.getEmergencyContactName())
                .emergencyContactNumber(request.getEmergencyContactNumber())
                .occupation(request.getOccupation())
                .active(true)
                .build();
        resident = residentRepository.save(resident);

        return ApiResponse.success(sendCredentials(user, tempPassword, "resident") ? "Resident created and credentials emailed" : "Resident created, but credential email was not sent", mapToResponse(resident));
    }

    @Override
    public ApiResponse<List<ResidentResponse>> getAllResidents(Long societyId) {
        List<Resident> residents = residentRepository.findAll().stream()
                .filter(r -> r.getFlat().getSociety().getId().equals(societyId) && r.getActive())
                .collect(Collectors.toList());
        List<ResidentResponse> responses = residents.stream().map(this::mapToResponse).collect(Collectors.toList());
        return ApiResponse.success("Residents fetched successfully", responses);
    }

    @Override
    @Transactional
    public ApiResponse<ResidentResponse> updateResident(Long residentId, CreateResidentRequest request) {
        Resident resident = residentRepository.findById(residentId)
                .orElseThrow(() -> new RuntimeException("Resident not found"));
        
        resident.setFullName(request.getFullName());
        resident.setMobileNumber(request.getMobileNumber());
        resident.setGender(request.getGender());
        resident.setResidentType(request.getResidentType());
        resident.setDateOfBirth(request.getDateOfBirth());
        resident.setEmergencyContactName(request.getEmergencyContactName());
        resident.setEmergencyContactNumber(request.getEmergencyContactNumber());
        resident.setOccupation(request.getOccupation());

        if (!resident.getFlat().getId().equals(request.getFlatId())) {
            Flat flat = flatRepository.findById(request.getFlatId())
                    .orElseThrow(() -> new RuntimeException("Flat not found"));
            resident.setFlat(flat);
        }

        resident = residentRepository.save(resident);
        return ApiResponse.success("Resident updated successfully", mapToResponse(resident));
    }

    @Override
    @Transactional
    public ApiResponse<String> deleteResident(Long residentId) {
        Resident resident = residentRepository.findById(residentId)
                .orElseThrow(() -> new RuntimeException("Resident not found"));
        resident.setActive(false);
        residentRepository.save(resident);
        releaseUserEmail(resident.getUser());
        return ApiResponse.success("Resident deleted and email released for reuse", null);
    }

    @Override
    @Transactional
    public ApiResponse<SecurityGuardResponse> createSecurityGuard(CreateSecurityGuardRequest request, Long societyId) {
        String email = request.getEmail().trim().toLowerCase();
        if (userRepository.existsByEmail(email)) {
            throw new BadRequestException("This email is already registered. Use a different email address for the security guard.");
        }
        Society society = societyRepository.findById(societyId)
                .orElseThrow(() -> new RuntimeException("Society not found"));

        String tempPassword = generateTempPassword();
        log.info("Temporary password for new security guard {}: {}", request.getEmail(), tempPassword);

        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(tempPassword))
                .role(UserRole.SECURITY_GUARD)
                .accountStatus(AccountStatus.ACTIVE)
                .emailVerified(false)
                .firstLogin(true)
                .build();
        user = userRepository.save(user);

        SecurityGuard guard = SecurityGuard.builder()
                .user(user)
                .society(society)
                .fullName(request.getFullName())
                .mobileNumber(request.getMobileNumber())
                .gender(request.getGender())
                .shiftType(request.getShiftType())
                .joiningDate(request.getJoiningDate())
                .employeeId(generateEmployeeId(society))
                .active(true)
                .build();
        guard = securityGuardRepository.save(guard);

        return ApiResponse.success(sendCredentials(user, tempPassword, "security guard") ? "Security guard created and credentials emailed" : "Security guard created, but credential email was not sent", mapToResponse(guard));
    }

    @Override
    public ApiResponse<List<SecurityGuardResponse>> getAllSecurityGuards(Long societyId) {
        List<SecurityGuard> guards = securityGuardRepository.findAll().stream()
                .filter(g -> g.getSociety().getId().equals(societyId) && g.getActive())
                .collect(Collectors.toList());
        List<SecurityGuardResponse> responses = guards.stream().map(this::mapToResponse).collect(Collectors.toList());
        return ApiResponse.success("Security Guards fetched successfully", responses);
    }

    @Override
    @Transactional
    public ApiResponse<SecurityGuardResponse> updateSecurityGuard(Long guardId, CreateSecurityGuardRequest request) {
        SecurityGuard guard = securityGuardRepository.findById(guardId)
                .orElseThrow(() -> new RuntimeException("Security Guard not found"));
        
        guard.setFullName(request.getFullName());
        guard.setMobileNumber(request.getMobileNumber());
        guard.setGender(request.getGender());
        guard.setShiftType(request.getShiftType());
        guard.setJoiningDate(request.getJoiningDate());
        guard.setEmployeeId(request.getEmployeeId());

        guard = securityGuardRepository.save(guard);
        return ApiResponse.success("Security Guard updated successfully", mapToResponse(guard));
    }

    @Override
    @Transactional
    public ApiResponse<String> deleteSecurityGuard(Long guardId) {
        SecurityGuard guard = securityGuardRepository.findById(guardId)
                .orElseThrow(() -> new RuntimeException("Security Guard not found"));
        guard.setActive(false);
        securityGuardRepository.save(guard);
        releaseUserEmail(guard.getUser());
        return ApiResponse.success("Security guard deleted and email released for reuse", null);
    }

    private void releaseUserEmail(User user) {
        String originalEmail = user.getEmail();
        user.setAccountStatus(AccountStatus.INACTIVE);
        user.setEmail("deleted-" + user.getId() + "-" + UUID.randomUUID().toString().substring(0, 8) + "@societysphere.invalid");
        userRepository.save(user);
        log.info("Released login email {} for deleted account {}", originalEmail, user.getId());
    }
    private String generateEmployeeId(Society society) {
        String prefix = "GRD-" + society.getId() + "-";
        long sequence = securityGuardRepository.countBySociety(society) + 1;
        String employeeId;
        do { employeeId = prefix + String.format("%04d", sequence++); }
        while (securityGuardRepository.existsByEmployeeId(employeeId));
        return employeeId;
    }
    private boolean sendCredentials(User user, String password, String role) {
        if (!mailEnabled || senderEmail == null || senderEmail.isBlank()) return false;
        try { SimpleMailMessage message = new SimpleMailMessage(); message.setFrom(senderEmail); message.setTo(user.getEmail()); message.setSubject("Society Sphere " + role + " credentials"); message.setText("Your Society Sphere account is ready.\n\nLogin email: " + user.getEmail() + "\nTemporary password: " + password + "\n\nPlease sign in and complete your profile."); mail.send(message); return true; }
        catch (MailException ex) { log.warn("Could not email {} credentials: {}", role, ex.getMessage()); return false; }
    }
    private ResidentResponse mapToResponse(Resident resident) {
        return ResidentResponse.builder()
                .id(resident.getId())
                .publicId(resident.getPublicId())
                .fullName(resident.getFullName())
                .email(resident.getUser().getEmail())
                .mobileNumber(resident.getMobileNumber())
                .gender(resident.getGender())
                .residentType(resident.getResidentType())
                .flatNumber(resident.getFlat().getFlatNumber())
                .blockName(resident.getFlat().getBlock())
                .societyName(resident.getFlat().getSociety().getSocietyName())
                .active(resident.getActive())
                .dateOfBirth(resident.getDateOfBirth())
                .occupation(resident.getOccupation())
                .profileImage(resident.getProfileImage())
                .alternateMobileNumber(resident.getAlternateMobileNumber())
                .emergencyContactName(resident.getEmergencyContactName())
                .emergencyContactNumber(resident.getEmergencyContactNumber())
                .build();
    }

    private SecurityGuardResponse mapToResponse(SecurityGuard guard) {
        return SecurityGuardResponse.builder()
                .id(guard.getId())
                .publicId(guard.getPublicId())
                .fullName(guard.getFullName())
                .email(guard.getUser().getEmail())
                .mobileNumber(guard.getMobileNumber())
                .gender(guard.getGender())
                .shiftType(guard.getShiftType())
                .joiningDate(guard.getJoiningDate())
                .employeeId(guard.getEmployeeId())
                .profileImage(guard.getProfileImage())
                .active(guard.getActive())
                .build();
    }
}
