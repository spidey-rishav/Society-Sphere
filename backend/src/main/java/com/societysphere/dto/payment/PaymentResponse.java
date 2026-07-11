package com.societysphere.dto.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.societysphere.enums.PaymentMethod;
import com.societysphere.enums.PaymentStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResponse {

    private Long paymentId;

    private Long complaintId;

    private String complaintTitle;

    private Long residentId;

    private String residentName;

    private Long societyId;

    private String societyName;

    private BigDecimal amount;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

    private LocalDateTime paymentDate;

    private String transactionId;

    private String remarks;

}