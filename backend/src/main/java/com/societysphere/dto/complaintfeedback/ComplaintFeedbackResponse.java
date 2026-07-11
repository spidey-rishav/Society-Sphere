package com.societysphere.dto.complaintfeedback;

import com.societysphere.enums.FeedbackRating;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComplaintFeedbackResponse {

    private Long feedbackId;

    private Long complaintId;

    private String complaintTitle;

    private Long residentId;

    private String residentName;

    private Long societyId;

    private String societyName;

    private FeedbackRating rating;

    private String feedbackComment;

}