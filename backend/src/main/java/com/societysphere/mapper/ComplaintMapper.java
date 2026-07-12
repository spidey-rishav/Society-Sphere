package com.societysphere.mapper;

import com.societysphere.dto.complaint.ComplaintRequest;
import com.societysphere.dto.complaint.ComplaintResponse;
import com.societysphere.entity.Complaint;

public final class ComplaintMapper {

    private ComplaintMapper() {
    }

    public static Complaint toEntity(ComplaintRequest request) {

        if (request == null) {
            return null;
        }

        Complaint complaint = new Complaint();

        complaint.setRaisedBy(request.getRaisedBy());
        complaint.setCategory(request.getCategory());
        complaint.setTitle(request.getTitle());
        complaint.setDescription(request.getDescription());

        return complaint;
    }

    public static ComplaintResponse toResponse(Complaint complaint) {

        if (complaint == null) {
            return null;
        }

        ComplaintResponse response = new ComplaintResponse();

        response.setId(complaint.getId());
        response.setPublicId(complaint.getPublicId());

        if (complaint.getSociety() != null) {
            response.setSocietyId(complaint.getSociety().getId());
            response.setSocietyName(complaint.getSociety().getSocietyName());
        }

        if (complaint.getFlat() != null) {
            response.setFlatId(complaint.getFlat().getId());
            response.setFlatNumber(complaint.getFlat().getFlatNumber());
        }

        if (complaint.getResident() != null) {
            response.setResidentId(complaint.getResident().getId());
            response.setResidentName(complaint.getResident().getFullName());
        }

        if (complaint.getSecurityGuard() != null) {
            response.setSecurityGuardId(complaint.getSecurityGuard().getId());
            response.setSecurityGuardName(complaint.getSecurityGuard().getFullName());
        }

        response.setRaisedBy(complaint.getRaisedBy());
        response.setCategory(complaint.getCategory());
        response.setTitle(complaint.getTitle());
        response.setDescription(complaint.getDescription());
        response.setStatus(complaint.getStatus());
        response.setPaymentRequired(complaint.getPaymentRequired());
        response.setEstimatedAmount(complaint.getEstimatedAmount());
        response.setResolvedAt(complaint.getResolvedAt());
        response.setAdminRemarks(complaint.getAdminRemarks());
        response.setResolutionSummary(complaint.getResolutionSummary());

        return response;
    }

}