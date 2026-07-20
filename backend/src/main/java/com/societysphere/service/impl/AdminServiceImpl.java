package com.societysphere.service.impl;

import com.societysphere.dto.resident.CreateResidentRequest;
import com.societysphere.dto.resident.ResidentResponse;
import com.societysphere.dto.securityguard.CreateSecurityGuardRequest;
import com.societysphere.dto.securityguard.SecurityGuardResponse;
import com.societysphere.entity.*;
import com.societysphere.enums.AccountStatus;
import com.societysphere.enums.UserRole;
import com.societysphere.repository.*;
import com.societysphere.response.ApiResponse;
import com.societysphere.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private String generateTempPassword() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    @Override
    @Transactional
    public ApiResponse<ResidentResponse> createResident(CreateResidentRequest request, Long societyId) {
        Flat flat = flatRepository.findById(request.getFlatId())
                .orElseThrow(() -> new RuntimeException("Flat not found"));

        if (!flat.getSociety().getId().equals(societyId)) {
            throw new RuntimeException("Flat does not belong to the given society");
        }

        String tempPassword = generateTempPassword();
        log.info("Temporary password for new resident {}: {}", request.getEmail(), tempPassword);

        User user = User.builder()
                .email(request.getEmail())
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

        return ApiResponse.success("Resident created successfully", mapToResponse(resident));
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
        return ApiResponse.success("Resident deleted successfully", null);
    }

    @Override
    @Transactional
    public ApiResponse<SecurityGuardResponse> createSecurityGuard(CreateSecurityGuardRequest request, Long societyId) {
        Society society = societyRepository.findById(societyId)
                .orElseThrow(() -> new RuntimeException("Society not found"));

        String tempPassword = generateTempPassword();
        log.info("Temporary password for new security guard {}: {}", request.getEmail(), tempPassword);

        User user = User.builder()
                .email(request.getEmail())
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
                .employeeId(request.getEmployeeId())
                .active(true)
                .build();
        guard = securityGuardRepository.save(guard);

        return ApiResponse.success("Security Guard created successfully", mapToResponse(guard));
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
        return ApiResponse.success("Security Guard deleted successfully", null);
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
