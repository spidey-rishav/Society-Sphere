package com.societysphere.mapper;

import com.societysphere.dto.complaintfeedback.ComplaintFeedbackRequest;
import com.societysphere.dto.complaintfeedback.ComplaintFeedbackResponse;
import com.societysphere.entity.ComplaintFeedback;

public final class ComplaintFeedbackMapper {

    private ComplaintFeedbackMapper() {
    }

    public static ComplaintFeedback toEntity(ComplaintFeedbackRequest request) {

        if (request == null) {
            return null;
        }

        ComplaintFeedback complaintFeedback = new ComplaintFeedback();

        complaintFeedback.setRating(request.getRating());
        complaintFeedback.setFeedbackComment(request.getFeedbackComment());

        return complaintFeedback;
    }

    public static ComplaintFeedbackResponse toResponse(ComplaintFeedback complaintFeedback) {

        if (complaintFeedback == null) {
            return null;
        }

        ComplaintFeedbackResponse response = new ComplaintFeedbackResponse();

        response.setFeedbackId(complaintFeedback.getFeedbackId());

        if (complaintFeedback.getComplaint() != null) {
            response.setComplaintId(complaintFeedback.getComplaint().getId());
            response.setComplaintTitle(complaintFeedback.getComplaint().getTitle());
        }

        if (complaintFeedback.getResident() != null) {
            response.setResidentId(complaintFeedback.getResident().getId());
            response.setResidentName(complaintFeedback.getResident().getFullName());
        }

        if (complaintFeedback.getSociety() != null) {
            response.setSocietyId(complaintFeedback.getSociety().getId());
            response.setSocietyName(complaintFeedback.getSociety().getSocietyName());
        }

        response.setRating(complaintFeedback.getRating());
        response.setFeedbackComment(complaintFeedback.getFeedbackComment());

        return response;
    }

}