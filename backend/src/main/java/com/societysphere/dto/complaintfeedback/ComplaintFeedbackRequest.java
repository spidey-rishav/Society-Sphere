package com.societysphere.dto.complaintfeedback;

import com.societysphere.enums.FeedbackRating;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComplaintFeedbackRequest {

    @NotNull(message = "Complaint is required")
    private Long complaintId;

    @NotNull(message = "Resident is required")
    private Long residentId;

    @NotNull(message = "Society is required")
    private Long societyId;

    @NotNull(message = "Rating is required")
    private FeedbackRating rating;

    private String feedbackComment;

}