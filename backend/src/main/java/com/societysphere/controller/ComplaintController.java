package com.societysphere.controller;

import com.societysphere.dto.complaint.ComplaintResponse;
import com.societysphere.dto.complaint.CreateComplaintRequest;
import com.societysphere.dto.complaint.UpdateComplaintRequest;
import com.societysphere.dto.complaintfeedback.CreateFeedbackRequest;
import com.societysphere.dto.payment.CreatePaymentRequest;
import com.societysphere.dto.payment.PaymentResponse;
import com.societysphere.response.ApiResponse;
import com.societysphere.service.ComplaintService;
import com.societysphere.service.FeedbackService;
import com.societysphere.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ComplaintController {

    private final ComplaintService complaintService;
    private final PaymentService paymentService;
    private final FeedbackService feedbackService;

    // TODO: Extract userEmail from authenticated user context, passing placeholder for now

    @PostMapping("/complaints")
    public ApiResponse<ComplaintResponse> raiseComplaint(@RequestBody CreateComplaintRequest request) {
        String userEmail = "test@example.com"; // Placeholder
        return complaintService.raiseComplaint(request, userEmail);
    }

    @GetMapping("/complaints/my")
    public ApiResponse<List<ComplaintResponse>> getMyComplaints() {
        String userEmail = "test@example.com"; // Placeholder
        return complaintService.getMyComplaints(userEmail);
    }

    @GetMapping("/complaints/residents")
    public ApiResponse<List<ComplaintResponse>> getResidentComplaints() {
        Long societyId = 1L; // Placeholder
        return complaintService.getResidentComplaints(societyId);
    }

    @GetMapping("/complaints/guards")
    public ApiResponse<List<ComplaintResponse>> getGuardComplaints() {
        Long societyId = 1L; // Placeholder
        return complaintService.getGuardComplaints(societyId);
    }

    @PutMapping("/complaints/{id}")
    public ApiResponse<ComplaintResponse> updateComplaint(@PathVariable("id") Long id, @RequestBody UpdateComplaintRequest request) {
        return complaintService.updateComplaint(id, request);
    }

    @PostMapping("/payments")
    public ApiResponse<PaymentResponse> processPayment(@RequestBody CreatePaymentRequest request) {
        String userEmail = "test@example.com"; // Placeholder
        return paymentService.processPayment(request, userEmail);
    }

    @PostMapping("/feedback")
    public ApiResponse<String> submitFeedback(@RequestBody CreateFeedbackRequest request) {
        String userEmail = "test@example.com"; // Placeholder
        return feedbackService.submitFeedback(request, userEmail);
    }
}
