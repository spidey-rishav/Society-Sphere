package com.societysphere.service;

import com.societysphere.dto.guestpreapproval.GuestPreApprovalResponse;
import com.societysphere.dto.guestpreapproval.GuestPreBookRequest;
import com.societysphere.response.ApiResponse;

import java.util.List;

public interface GuestPreApprovalService {
    ApiResponse<GuestPreApprovalResponse> preBookGuest(GuestPreBookRequest request);
    ApiResponse<String> approveGuestPreBook(Long preApprovalId, boolean approved);
    ApiResponse<List<GuestPreApprovalResponse>> getMyGuestPreApprovals(Long residentId);
}
