package com.societysphere.controller;

import com.societysphere.dto.notice.CreateNoticeRequest;
import com.societysphere.dto.notice.NoticeResponse;
import com.societysphere.dto.admin.AdminResponse;
import com.societysphere.dto.admin.CreateSocietyAdminRequest;
import com.societysphere.dto.flat.FlatResponse;
import com.societysphere.dto.flat.CreateAdminFlatRequest;
import com.societysphere.dto.resident.CreateResidentRequest;
import com.societysphere.dto.resident.ResidentResponse;
import com.societysphere.dto.securityguard.CreateSecurityGuardRequest;
import com.societysphere.dto.securityguard.SecurityGuardResponse;
import com.societysphere.entity.Admin;
import com.societysphere.entity.Flat;
import com.societysphere.entity.User;
import com.societysphere.enums.FlatOccupancyStatus;
import com.societysphere.enums.AccountStatus;
import com.societysphere.enums.AdminType;
import com.societysphere.enums.UserRole;
import com.societysphere.exception.BadRequestException;
import com.societysphere.mapper.FlatMapper;
import com.societysphere.repository.AdminRepository;
import com.societysphere.repository.FlatRepository;
import com.societysphere.repository.UserRepository;
import com.societysphere.response.ApiResponse;
import com.societysphere.service.AdminService;
import com.societysphere.service.NoticeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN') or hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;
    private final NoticeService noticeService;
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final FlatRepository flatRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mail;

    @Value("${app.mail.enabled:false}")
    private boolean mailEnabled;

    @Value("${spring.mail.username:}")
    private String senderEmail;

    private Admin getAuthenticatedAdmin(UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return adminRepository.findAll().stream()
                .filter(a -> a.getUser().getId().equals(user.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Admin not found"));
    }

    @GetMapping("/flats")
    public ResponseEntity<ApiResponse<List<FlatResponse>>> getFlatsForAuthenticatedAdmin(
            @AuthenticationPrincipal UserDetails userDetails) {
        Admin admin = getAuthenticatedAdmin(userDetails);
        List<FlatResponse> flats = flatRepository.findBySociety(admin.getSociety()).stream()
                .filter(flat -> Boolean.TRUE.equals(flat.getActive()))
                .map(FlatMapper::toResponse)
                .toList();
        return ResponseEntity.ok(ApiResponse.success("Flats fetched successfully", flats));
    }

    @GetMapping("/admins")
    public ResponseEntity<ApiResponse<List<AdminResponse>>> getSocietyAdmins(
            @AuthenticationPrincipal UserDetails userDetails) {
        Admin currentAdmin = getAuthenticatedAdmin(userDetails);
        List<AdminResponse> response = adminRepository.findBySociety(currentAdmin.getSociety()).stream()
                .map(this::toAdminResponse)
                .toList();
        return ResponseEntity.ok(ApiResponse.success("Administrators fetched successfully", response));
    }

    @PostMapping("/admins")
    public ResponseEntity<ApiResponse<AdminResponse>> createSocietyAdmin(
            @Valid @RequestBody CreateSocietyAdminRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        Admin currentAdmin = getAuthenticatedAdmin(userDetails);
        String email = request.getEmail().trim().toLowerCase();
        if (userRepository.existsByEmail(email)) {
            throw new BadRequestException("This email is already registered. Use a different email address.");
        }

        String temporaryPassword = "SS-" + Integer.toHexString(new SecureRandom().nextInt()).toUpperCase();
        User user = userRepository.save(User.builder()
                .email(email)
                .password(passwordEncoder.encode(temporaryPassword))
                .role(UserRole.ADMIN)
                .accountStatus(AccountStatus.ACTIVE)
                .emailVerified(false)
                .firstLogin(true)
                .build());
        Admin newAdmin = adminRepository.save(Admin.builder()
                .user(user)
                .society(currentAdmin.getSociety())
                .fullName(request.getFullName().trim())
                .mobileNumber(request.getMobileNumber().trim())
                .adminType(AdminType.ADMIN)
                .build());

        boolean emailed = sendAdminCredentials(user, temporaryPassword);
        String message = emailed ? "Administrator created and temporary credentials emailed" : "Administrator created, but the credentials email was not sent";
        return ResponseEntity.ok(ApiResponse.success(message, toAdminResponse(newAdmin)));
    }

    @PostMapping("/flats")
    public ResponseEntity<ApiResponse<FlatResponse>> createFlat(
            @Valid @RequestBody CreateAdminFlatRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        Admin admin = getAuthenticatedAdmin(userDetails);
        if (flatRepository.existsByFlatNumberAndSociety(request.getFlatNumber().trim(), admin.getSociety())) {
            throw new BadRequestException("This flat number already exists in your society");
        }
        Flat flat = Flat.builder()
                .society(admin.getSociety())
                .flatNumber(request.getFlatNumber().trim())
                .block(request.getBlockName().trim())
                .floorNumber(request.getFloorNumber())
                .occupancyStatus(FlatOccupancyStatus.VACANT)
                .active(true)
                .build();
        return ResponseEntity.ok(ApiResponse.success("Flat created successfully", FlatMapper.toResponse(flatRepository.save(flat))));
    }

    @PostMapping("/residents")
    public ResponseEntity<ApiResponse<ResidentResponse>> createResident(
            @Valid @RequestBody CreateResidentRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        Admin admin = getAuthenticatedAdmin(userDetails);
        return ResponseEntity.ok(adminService.createResident(request, admin.getSociety().getId()));
    }

    @GetMapping("/residents")
    public ResponseEntity<ApiResponse<List<ResidentResponse>>> getAllResidents(
            @AuthenticationPrincipal UserDetails userDetails) {
        Admin admin = getAuthenticatedAdmin(userDetails);
        return ResponseEntity.ok(adminService.getAllResidents(admin.getSociety().getId()));
    }

    @PutMapping("/residents/{id}")
    public ResponseEntity<ApiResponse<ResidentResponse>> updateResident(
            @PathVariable Long id,
            @Valid @RequestBody CreateResidentRequest request) {
        return ResponseEntity.ok(adminService.updateResident(id, request));
    }

    @DeleteMapping("/residents/{id}")
    public ResponseEntity<ApiResponse<String>> deleteResident(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.deleteResident(id));
    }

    @PostMapping("/guards")
    public ResponseEntity<ApiResponse<SecurityGuardResponse>> createSecurityGuard(
            @Valid @RequestBody CreateSecurityGuardRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        Admin admin = getAuthenticatedAdmin(userDetails);
        return ResponseEntity.ok(adminService.createSecurityGuard(request, admin.getSociety().getId()));
    }

    @GetMapping("/guards")
    public ResponseEntity<ApiResponse<List<SecurityGuardResponse>>> getAllSecurityGuards(
            @AuthenticationPrincipal UserDetails userDetails) {
        Admin admin = getAuthenticatedAdmin(userDetails);
        return ResponseEntity.ok(adminService.getAllSecurityGuards(admin.getSociety().getId()));
    }

    @PutMapping("/guards/{id}")
    public ResponseEntity<ApiResponse<SecurityGuardResponse>> updateSecurityGuard(
            @PathVariable Long id,
            @Valid @RequestBody CreateSecurityGuardRequest request) {
        return ResponseEntity.ok(adminService.updateSecurityGuard(id, request));
    }

    @DeleteMapping("/guards/{id}")
    public ResponseEntity<ApiResponse<String>> deleteSecurityGuard(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.deleteSecurityGuard(id));
    }

    @PostMapping("/notices")
    public ResponseEntity<ApiResponse<NoticeResponse>> createNotice(
            @Valid @RequestBody CreateNoticeRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        Admin admin = getAuthenticatedAdmin(userDetails);
        return ResponseEntity.ok(noticeService.createNotice(request, admin.getId(), admin.getSociety().getId()));
    }

    @GetMapping("/notices")
    public ResponseEntity<ApiResponse<List<NoticeResponse>>> getNoticesForSociety(
            @AuthenticationPrincipal UserDetails userDetails) {
        Admin admin = getAuthenticatedAdmin(userDetails);
        return ResponseEntity.ok(noticeService.getNoticesForSociety(admin.getSociety().getId()));
    }

    @PutMapping("/notices/{id}")
    public ResponseEntity<ApiResponse<NoticeResponse>> updateNotice(
            @PathVariable Long id,
            @Valid @RequestBody CreateNoticeRequest request) {
        return ResponseEntity.ok(noticeService.updateNotice(id, request));
    }

    @DeleteMapping("/notices/{id}")
    public ResponseEntity<ApiResponse<String>> deleteNotice(@PathVariable Long id) {
        return ResponseEntity.ok(noticeService.deleteNotice(id));
    }

    private AdminResponse toAdminResponse(Admin admin) {
        AdminResponse response = new AdminResponse();
        response.setId(admin.getId());
        response.setUserId(admin.getUser().getId());
        response.setFullName(admin.getFullName());
        response.setEmail(admin.getUser().getEmail());
        response.setMobileNumber(admin.getMobileNumber());
        response.setSocietyId(admin.getSociety().getId());
        response.setSocietyName(admin.getSociety().getSocietyName());
        response.setAdminType(admin.getAdminType());
        response.setActive(admin.getUser().getAccountStatus() == AccountStatus.ACTIVE);
        return response;
    }

    private boolean sendAdminCredentials(User user, String password) {
        if (!mailEnabled || senderEmail == null || senderEmail.isBlank()) {
            return false;
        }
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(senderEmail);
            message.setTo(user.getEmail());
            message.setSubject("Society Sphere administrator credentials");
            message.setText("Your Society Sphere administrator account is ready.\n\nLogin email: " + user.getEmail()
                    + "\nTemporary password: " + password + "\n\nPlease sign in and complete your profile.");
            mail.send(message);
            return true;
        } catch (MailException ex) {
            return false;
        }
    }
}
