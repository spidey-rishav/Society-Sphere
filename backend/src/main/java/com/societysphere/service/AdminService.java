package com.societysphere.service;

import com.societysphere.dto.resident.CreateResidentRequest;
import com.societysphere.dto.resident.ResidentResponse;
import com.societysphere.dto.securityguard.CreateSecurityGuardRequest;
import com.societysphere.dto.securityguard.SecurityGuardResponse;
import com.societysphere.response.ApiResponse;

import java.util.List;

public interface AdminService {
    ApiResponse<ResidentResponse> createResident(CreateResidentRequest request, Long societyId);
    ApiResponse<List<ResidentResponse>> getAllResidents(Long societyId);
    ApiResponse<ResidentResponse> updateResident(Long residentId, CreateResidentRequest request);
    ApiResponse<String> deleteResident(Long residentId);

    ApiResponse<SecurityGuardResponse> createSecurityGuard(CreateSecurityGuardRequest request, Long societyId);
    ApiResponse<List<SecurityGuardResponse>> getAllSecurityGuards(Long societyId);
    ApiResponse<SecurityGuardResponse> updateSecurityGuard(Long guardId, CreateSecurityGuardRequest request);
    ApiResponse<String> deleteSecurityGuard(Long guardId);
}