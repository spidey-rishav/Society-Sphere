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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ComplaintController {

    private final ComplaintService complaintService;
    private final PaymentService paymentService;
    private final FeedbackService feedbackService;

    @PostMapping("/complaints")
    public ApiResponse<ComplaintResponse> raiseComplaint(
            @RequestBody CreateComplaintRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return complaintService.raiseComplaint(request, userDetails.getUsername());
    }

    @GetMapping("/complaints/my")
    public ApiResponse<List<ComplaintResponse>> getMyComplaints(
            @AuthenticationPrincipal UserDetails userDetails) {
        return complaintService.getMyComplaints(userDetails.getUsername());
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
    public ApiResponse<ComplaintResponse> updateComplaint(
            @PathVariable("id") Long id,
            @RequestBody UpdateComplaintRequest request) {
        return complaintService.updateComplaint(id, request);
    }

    @PostMapping("/payments")
    public ApiResponse<PaymentResponse> processPayment(
            @RequestBody CreatePaymentRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return paymentService.processPayment(request, userDetails.getUsername());
    }

    @PostMapping("/feedback")
    public ApiResponse<String> submitFeedback(
            @RequestBody CreateFeedbackRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return feedbackService.submitFeedback(request, userDetails.getUsername());
    }
}