package com.societysphere.dto.subscription;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionRequest {

    @NotNull(message = "Society is required")
    private Long societyId;

    @NotNull(message = "Subscription plan is required")
    private Long subscriptionPlanId;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

}