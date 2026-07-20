package com.societysphere.controller;

import com.societysphere.dto.notice.CreateNoticeRequest;
import com.societysphere.dto.notice.NoticeResponse;
import com.societysphere.dto.resident.CreateResidentRequest;
import com.societysphere.dto.resident.ResidentResponse;
import com.societysphere.dto.securityguard.CreateSecurityGuardRequest;
import com.societysphere.dto.securityguard.SecurityGuardResponse;
import com.societysphere.entity.Admin;
import com.societysphere.entity.User;
import com.societysphere.repository.AdminRepository;
import com.societysphere.repository.UserRepository;
import com.societysphere.response.ApiResponse;
import com.societysphere.service.AdminService;
import com.societysphere.service.NoticeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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

    private Admin getAuthenticatedAdmin(UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return adminRepository.findAll().stream()
                .filter(a -> a.getUser().getId().equals(user.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Admin not found"));
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
}
