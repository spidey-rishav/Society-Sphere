package com.societysphere.mapper;

import com.societysphere.dto.complaintfeedback.CreateFeedbackRequest;
import com.societysphere.entity.ComplaintFeedback;

public final class ComplaintFeedbackMapper {

    private ComplaintFeedbackMapper() {
    }

    public static ComplaintFeedback toEntity(CreateFeedbackRequest request) {

        if (request == null) {
            return null;
        }

        ComplaintFeedback complaintFeedback = new ComplaintFeedback();
        complaintFeedback.setRating(request.getRating());
        complaintFeedback.setFeedbackComment(request.getComment());

        return complaintFeedback;
    }
}