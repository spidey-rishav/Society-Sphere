
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

        PaymentResponse response = new PaymentResponse();

        response.setPaymentId(payment.getPaymentId());

        if (payment.getComplaint() != null) {
            response.setComplaintId(payment.getComplaint().getId());
            response.setComplaintTitle(payment.getComplaint().getTitle());
        }

        if (payment.getResident() != null) {
            response.setResidentId(payment.getResident().getId());
            response.setResidentName(payment.getResident().getFullName());
        }

        if (payment.getSociety() != null) {
            response.setSocietyId(payment.getSociety().getId());
            response.setSocietyName(payment.getSociety().getSocietyName());
        }

        response.setAmount(payment.getAmount());
        response.setPaymentMethod(payment.getPaymentMethod());
        response.setPaymentStatus(payment.getPaymentStatus());
        response.setPaymentDate(payment.getPaymentDate());
        response.setTransactionId(payment.getTransactionId());
        response.setRemarks(payment.getRemarks());

        return response;
    }

}