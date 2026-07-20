package com.societysphere.dto.payment;

import com.societysphere.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreatePaymentRequest {

    @NotNull(message = "Complaint ID is required")
    private Long complaintId;

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;

    @NotNull(message = "Amount is required")
    private BigDecimal amount;
}
