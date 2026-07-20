package com.societysphere.controller;

import com.societysphere.dto.notice.NoticeResponse;
import com.societysphere.entity.Resident;
import com.societysphere.entity.SecurityGuard;
import com.societysphere.entity.User;
import com.societysphere.enums.NoticeAudience;
import com.societysphere.enums.UserRole;
import com.societysphere.repository.ResidentRepository;
import com.societysphere.repository.SecurityGuardRepository;
import com.societysphere.repository.UserRepository;
import com.societysphere.response.ApiResponse;
import com.societysphere.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notices")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;
    private final UserRepository userRepository;
    private final ResidentRepository residentRepository;
    private final SecurityGuardRepository securityGuardRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<List<NoticeResponse>>> getNoticesForAuthenticatedUser(
            @AuthenticationPrincipal UserDetails userDetails) {
        
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() == UserRole.RESIDENT) {
            Resident resident = residentRepository.findAll().stream()
                    .filter(r -> r.getUser().getId().equals(user.getId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Resident not found"));
            
            return ResponseEntity.ok(noticeService.getNoticesForAudience(
                    resident.getFlat().getSociety().getId(), NoticeAudience.RESIDENT));
        } else if (user.getRole() == UserRole.SECURITY_GUARD) {
            SecurityGuard guard = securityGuardRepository.findAll().stream()
                    .filter(g -> g.getUser().getId().equals(user.getId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Security Guard not found"));
            
            return ResponseEntity.ok(noticeService.getNoticesForAudience(
                    guard.getSociety().getId(), NoticeAudience.SECURITY_GUARD));
        }
        
        return ResponseEntity.badRequest().body(ApiResponse.error("Invalid role for this endpoint"));
    }
}
