package com.societysphere.service;

import com.societysphere.dto.visitor.AddDeliveryVisitorRequest;
import com.societysphere.dto.visitor.GuestVerifyRequest;
import com.societysphere.dto.visitor.VisitorResponse;
import com.societysphere.response.ApiResponse;

import java.util.List;

public interface VisitorService {
    ApiResponse<VisitorResponse> addDeliveryVisitor(AddDeliveryVisitorRequest request, Long guardId);
    ApiResponse<String> approveVisitor(Long visitorId, boolean approved);
    ApiResponse<String> recordExit(Long visitorId);
    ApiResponse<VisitorResponse> verifyGuestBarcode(String barcode);
    ApiResponse<VisitorResponse> checkInGuest(GuestVerifyRequest request, Long guardId);
    ApiResponse<List<VisitorResponse>> getVisitorsForFlat(Long flatId);
    ApiResponse<List<VisitorResponse>> getAllVisitors(Long societyId);
    ApiResponse<List<VisitorResponse>> getPendingApprovals(Long residentId);
}
