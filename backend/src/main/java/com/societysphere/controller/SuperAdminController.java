package com.societysphere.controller;

import com.societysphere.entity.Admin;
import com.societysphere.entity.Flat;
import com.societysphere.entity.Resident;
import com.societysphere.entity.SecurityGuard;
import com.societysphere.entity.Society;
import com.societysphere.enums.AccountStatus;
import com.societysphere.enums.RegistrationStatus;
import com.societysphere.repository.AdminRepository;
import com.societysphere.repository.FlatRepository;
import com.societysphere.repository.ResidentRepository;
import com.societysphere.repository.SecurityGuardRepository;
import com.societysphere.repository.SocietyRepository;
import com.societysphere.repository.UserRepository;
import com.societysphere.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/superadmin")
@RequiredArgsConstructor
public class SuperAdminController {
    private final SocietyRepository societyRepository;
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final ResidentRepository residentRepository;
    private final SecurityGuardRepository securityGuardRepository;
    private final FlatRepository flatRepository;

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
    @Transactional(readOnly = true)
    public ApiResponse<Map<String, Object>> getSocietyDetails(@PathVariable Long id) {
        Society society = societyRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Society not found"));
        Map<String, Object> details = new LinkedHashMap<>(toDashboardSociety(society));
        List<Admin> admins = adminRepository.findBySociety(society);
        List<Flat> flats = flatRepository.findBySociety(society);
        List<Resident> residents = residentRepository.findByFlatSociety(society);
        List<SecurityGuard> guards = securityGuardRepository.findBySociety(society);
        details.put("admins", admins.stream().map(this::toAdminDetail).toList());
        details.put("flats", flats.stream().map(this::toFlatDetail).toList());
        details.put("residents", residents.stream().map(this::toResidentDetail).toList());
        details.put("guards", guards.stream().map(this::toGuardDetail).toList());
        details.put("counts", Map.of("admins", admins.size(), "flats", flats.size(), "residents", residents.size(), "guards", guards.size()));
        return ApiResponse.success("Society management details retrieved", details);
    }

    private Map<String, Object> toAdminDetail(Admin admin) {
        Map<String, Object> detail = new LinkedHashMap<>();
        detail.put("id", admin.getId());
        detail.put("fullName", admin.getFullName());
        detail.put("email", admin.getUser().getEmail());
        detail.put("mobileNumber", admin.getMobileNumber());
        detail.put("adminType", admin.getAdminType().name());
        detail.put("accountStatus", admin.getUser().getAccountStatus().name());
        return detail;
    }

    private Map<String, Object> toFlatDetail(Flat flat) {
        Map<String, Object> detail = new LinkedHashMap<>();
        detail.put("id", flat.getId());
        detail.put("block", flat.getBlock());
        detail.put("flatNumber", flat.getFlatNumber());
        detail.put("floorNumber", flat.getFloorNumber());
        detail.put("occupancyStatus", flat.getOccupancyStatus().name());
        detail.put("active", flat.getActive());
        return detail;
    }

    private Map<String, Object> toResidentDetail(Resident resident) {
        Map<String, Object> detail = new LinkedHashMap<>();
        detail.put("id", resident.getId());
        detail.put("fullName", resident.getFullName());
        detail.put("email", resident.getUser().getEmail());
        detail.put("mobileNumber", resident.getMobileNumber());
        detail.put("alternateMobileNumber", resident.getAlternateMobileNumber());
        detail.put("gender", resident.getGender().name());
        detail.put("residentType", resident.getResidentType().name());
        detail.put("dateOfBirth", resident.getDateOfBirth());
        detail.put("occupation", resident.getOccupation());
        detail.put("flatNumber", resident.getFlat().getFlatNumber());
        detail.put("block", resident.getFlat().getBlock());
        detail.put("emergencyContactName", resident.getEmergencyContactName());
        detail.put("emergencyContactNumber", resident.getEmergencyContactNumber());
        detail.put("active", resident.getActive());
        return detail;
    }

    private Map<String, Object> toGuardDetail(SecurityGuard guard) {
        Map<String, Object> detail = new LinkedHashMap<>();
        detail.put("id", guard.getId());
        detail.put("fullName", guard.getFullName());
        detail.put("email", guard.getUser().getEmail());
        detail.put("mobileNumber", guard.getMobileNumber());
        detail.put("gender", guard.getGender().name());
        detail.put("employeeId", guard.getEmployeeId());
        detail.put("shiftType", guard.getShiftType().name());
        detail.put("joiningDate", guard.getJoiningDate());
        detail.put("active", guard.getActive());
        return detail;
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
