package com.societysphere.controller;

import com.societysphere.entity.Society;
import com.societysphere.enums.AccountStatus;
import com.societysphere.enums.RegistrationStatus;
import com.societysphere.repository.SocietyRepository;
import com.societysphere.repository.UserRepository;
import com.societysphere.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/superadmin")
@RequiredArgsConstructor
public class SuperAdminController {
    private final SocietyRepository societyRepository;
    private final UserRepository userRepository;

    @GetMapping("/stats")
    public ApiResponse<Map<String, Long>> getPlatformStats() {
        long totalSocieties = societyRepository.count();
        long activeSocieties = societyRepository.findAll().stream().filter(society -> Boolean.TRUE.equals(society.getActive())).count();
        return ApiResponse.success("Platform statistics retrieved", Map.of("totalSocieties", totalSocieties, "activeSocieties", activeSocieties, "pendingRequests", totalSocieties - activeSocieties, "totalUsers", userRepository.countByAccountStatus(AccountStatus.ACTIVE)));
    }

    @GetMapping("/societies")
    public ApiResponse<List<Map<String, Object>>> getSocieties() {
        return ApiResponse.success("Societies retrieved", societyRepository.findAll().stream().map(this::toDashboardSociety).toList());
    }

    @GetMapping("/societies/{id}")
    public ApiResponse<Map<String, Object>> getSocietyDetails(@PathVariable Long id) {
        Society society = societyRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Society not found"));
        return ApiResponse.success("Society details retrieved", toDashboardSociety(society));
    }

    private Map<String, Object> toDashboardSociety(Society society) {
        String status = society.getRegistrationStatus() != null ? society.getRegistrationStatus().name() : (Boolean.TRUE.equals(society.getActive()) ? RegistrationStatus.APPROVED.name() : RegistrationStatus.PENDING.name());
        return Map.ofEntries(
                Map.entry("id", society.getId()), Map.entry("name", society.getSocietyName()), Map.entry("code", society.getSocietyCode()),
                Map.entry("registrationNumber", society.getRegistrationNumber()), Map.entry("address", society.getAddressLine1()), Map.entry("city", society.getCity()),
                Map.entry("state", society.getState()), Map.entry("pincode", society.getPincode()), Map.entry("adminName", society.getApplicantAdminName() == null ? "Not provided" : society.getApplicantAdminName()),
                Map.entry("adminEmail", society.getEmail()), Map.entry("adminPhone", society.getPhoneNumber()),
                Map.entry("users", society.getAdmins().size() + society.getSecurityGuards().size()), Map.entry("status", status));
    }
}