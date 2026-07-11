package com.societysphere.dto.subscription;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.societysphere.enums.SubscriptionStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionResponse {

    private Long id;

    private Long societyId;

    private String societyName;

    private Long subscriptionPlanId;

    private String subscriptionPlanName;

    private BigDecimal price;

    private LocalDate startDate;

    private LocalDate endDate;

    private SubscriptionStatus status;

}