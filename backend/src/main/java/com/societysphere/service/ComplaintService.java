package com.societysphere.service;

import com.societysphere.dto.complaint.ComplaintResponse;
import com.societysphere.dto.complaint.CreateComplaintRequest;
import com.societysphere.dto.complaint.UpdateComplaintRequest;
import com.societysphere.response.ApiResponse;

import java.util.List;

public interface ComplaintService {
    ApiResponse<ComplaintResponse> raiseComplaint(CreateComplaintRequest request, String userEmail);
    ApiResponse<List<ComplaintResponse>> getMyComplaints(String userEmail);
    ApiResponse<List<ComplaintResponse>> getResidentComplaints(Long societyId);
    ApiResponse<List<ComplaintResponse>> getGuardComplaints(Long societyId);
    ApiResponse<ComplaintResponse> updateComplaint(Long complaintId, UpdateComplaintRequest request);
}
