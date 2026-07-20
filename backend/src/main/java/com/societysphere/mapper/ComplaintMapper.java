package com.societysphere.mapper;

import com.societysphere.dto.complaint.CreateComplaintRequest;
import com.societysphere.dto.complaint.ComplaintResponse;
import com.societysphere.entity.Complaint;

public final class ComplaintMapper {

    private ComplaintMapper() {
    }

    public static Complaint toEntity(CreateComplaintRequest request) {

        if (request == null) {
            return null;
        }

        Complaint complaint = new Complaint();
        complaint.setCategory(request.getCategory());
        complaint.setTitle(request.getTitle());
        complaint.setDescription(request.getDescription());

        return complaint;
    }

    public static ComplaintResponse toResponse(Complaint complaint) {

        if (complaint == null) {
            return null;
        }

        return ComplaintResponse.builder()
                .id(complaint.getId())
                .publicId(complaint.getPublicId())
                .title(complaint.getTitle())
                .description(complaint.getDescription())
                .category(complaint.getCategory())
                .status(complaint.getStatus())
                .raisedBy(complaint.getRaisedBy())
                .paymentRequired(complaint.getPaymentRequired())
                .estimatedAmount(complaint.getEstimatedAmount())
                .adminRemarks(complaint.getAdminRemarks())
                .resolutionSummary(complaint.getResolutionSummary())
                .resolvedAt(complaint.getResolvedAt())
                .createdAt(complaint.getCreatedAt())
                .flatNumber(complaint.getFlat() != null ? complaint.getFlat().getFlatNumber() : null)
                .blockName(complaint.getFlat() != null ? complaint.getFlat().getBlock() : null)
                .raisedByName(complaint.getResident() != null ? complaint.getResident().getFullName()
                        : complaint.getSecurityGuard() != null ? complaint.getSecurityGuard().getFullName() : null)
                .build();
    }
}