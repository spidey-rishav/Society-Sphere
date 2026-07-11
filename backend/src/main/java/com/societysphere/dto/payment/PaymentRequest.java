package com.societysphere.dto.payment;

import java.math.BigDecimal;

import com.societysphere.enums.PaymentMethod;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {

    @NotNull(message = "Complaint is required")
    private Long complaintId;

    @NotNull(message = "Resident is required")
    private Long residentId;

    @NotNull(message = "Society is required")
    private Long societyId;

    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;

    @NotBlank(message = "Transaction ID is required")
    private String transactionId;

    private String remarks;

}