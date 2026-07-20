
package com.societysphere.mapper;

import com.societysphere.dto.payment.PaymentRequest;
import com.societysphere.dto.payment.PaymentResponse;
import com.societysphere.entity.Payment;

public final class PaymentMapper {

    private PaymentMapper() {
    }

    public static Payment toEntity(PaymentRequest request) {

        if (request == null) {
            return null;
        }

        Payment payment = new Payment();

        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setTransactionId(request.getTransactionId());
        payment.setRemarks(request.getRemarks());

        return payment;
    }

    public static PaymentResponse toResponse(Payment payment) {

        if (payment == null) {
            return null;
        }

        PaymentResponse.PaymentResponseBuilder builder = PaymentResponse.builder()
                .id(payment.getPaymentId());

        if (payment.getComplaint() != null) {
            builder.complaintId(payment.getComplaint().getId());
        }

        return builder
                .amount(payment.getAmount())
                .paymentMethod(payment.getPaymentMethod())
                .paymentStatus(payment.getPaymentStatus())
                .build();
    }

}