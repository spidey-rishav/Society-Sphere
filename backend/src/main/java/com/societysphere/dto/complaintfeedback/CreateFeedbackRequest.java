package com.societysphere.dto.complaintfeedback;

import com.societysphere.enums.FeedbackRating;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateFeedbackRequest {

    @NotNull(message = "Complaint ID is required")
    private Long complaintId;

    @NotNull(message = "Rating is required")
    private FeedbackRating rating;

    private String comment;
}
