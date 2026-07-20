package com.societysphere.service;

import com.societysphere.dto.complaintfeedback.CreateFeedbackRequest;
import com.societysphere.response.ApiResponse;

public interface FeedbackService {
    ApiResponse<String> submitFeedback(CreateFeedbackRequest request, String userEmail);
}
