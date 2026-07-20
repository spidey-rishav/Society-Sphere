package com.societysphere.service.impl;

import com.societysphere.dto.complaintfeedback.CreateFeedbackRequest;
import com.societysphere.entity.Complaint;
import com.societysphere.entity.ComplaintFeedback;
import com.societysphere.entity.Resident;
import com.societysphere.enums.ComplaintStatus;
import com.societysphere.repository.ComplaintFeedbackRepository;
import com.societysphere.repository.ComplaintRepository;
import com.societysphere.repository.ResidentRepository;
import com.societysphere.response.ApiResponse;
import com.societysphere.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final ComplaintFeedbackRepository feedbackRepository;
    private final ComplaintRepository complaintRepository;
    private final ResidentRepository residentRepository;

    @Override
    @Transactional
    public ApiResponse<String> submitFeedback(CreateFeedbackRequest request, String userEmail) {
        Optional<Complaint> complaintOpt = complaintRepository.findById(request.getComplaintId());
        if (complaintOpt.isEmpty()) {
            return ApiResponse.error("Complaint not found");
        }
        
        Complaint complaint = complaintOpt.get();
        
        Optional<Resident> residentOpt = residentRepository.findAll().stream()
                .filter(r -> r.getUser().getEmail().equals(userEmail)).findFirst();
        if (residentOpt.isEmpty()) {
            return ApiResponse.error("Resident not found");
        }

        ComplaintFeedback feedback = ComplaintFeedback.builder()
                .complaint(complaint)
                .resident(residentOpt.get())
                .society(complaint.getSociety())
                .rating(request.getRating())
                .feedbackComment(request.getComment())
                .build();

        feedbackRepository.save(feedback);
        
        complaint.setStatus(ComplaintStatus.CLOSED);
        complaintRepository.save(complaint);

        return ApiResponse.success("Feedback submitted successfully", null);
    }
}
