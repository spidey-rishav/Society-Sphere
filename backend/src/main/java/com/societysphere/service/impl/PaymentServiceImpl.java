package com.societysphere.service.impl;

import com.societysphere.dto.payment.CreatePaymentRequest;
import com.societysphere.dto.payment.PaymentResponse;
import com.societysphere.entity.Complaint;
import com.societysphere.entity.Payment;
import com.societysphere.entity.Resident;
import com.societysphere.enums.ComplaintStatus;
import com.societysphere.enums.PaymentStatus;
import com.societysphere.repository.ComplaintRepository;
import com.societysphere.repository.PaymentRepository;
import com.societysphere.repository.ResidentRepository;
import com.societysphere.response.ApiResponse;
import com.societysphere.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final ComplaintRepository complaintRepository;
    private final ResidentRepository residentRepository;

    @Override
    @Transactional
    public ApiResponse<PaymentResponse> processPayment(CreatePaymentRequest request, String userEmail) {
        Optional<Complaint> complaintOpt = complaintRepository.findById(request.getComplaintId());
        if (complaintOpt.isEmpty()) {
            return ApiResponse.error("Complaint not found");
        }
        
        Complaint complaint = complaintOpt.get();
        if (!Boolean.TRUE.equals(complaint.getPaymentRequired())) {
            return ApiResponse.error("Payment is not required for this complaint");
        }
        if (complaint.getPayment() != null) {
            return ApiResponse.error("Payment already made");
        }

        Optional<Resident> residentOpt = residentRepository.findAll().stream()
                .filter(r -> r.getUser().getEmail().equals(userEmail)).findFirst();
        if (residentOpt.isEmpty()) {
            return ApiResponse.error("Resident not found");
        }

        Payment payment = Payment.builder()
                .complaint(complaint)
                .resident(residentOpt.get())
                .society(complaint.getSociety())
                .amount(request.getAmount())
                .paymentMethod(request.getPaymentMethod())
                .paymentStatus(PaymentStatus.SUCCESS)
                .paymentDate(LocalDateTime.now())
                .transactionId(UUID.randomUUID().toString())
                .build();

        payment = paymentRepository.save(payment);
        
        complaint.setStatus(ComplaintStatus.IN_PROGRESS);
        complaintRepository.save(complaint);

        PaymentResponse response = PaymentResponse.builder()
                .id(payment.getPaymentId())
                .complaintId(complaint.getId())
                .amount(payment.getAmount())
                .paymentMethod(payment.getPaymentMethod())
                .paymentStatus(payment.getPaymentStatus())
                .transactionId(payment.getTransactionId())
                .createdAt(payment.getCreatedAt())
                .build();

        return ApiResponse.success("Payment successful", response);
    }
}
