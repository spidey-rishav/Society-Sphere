package com.societysphere.dto.subscription;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionPlanResponse {

    private Long id;

    private String planName;

    private String description;

    private BigDecimal price;

    private Integer durationInMonths;

    private Integer maxFlats;

    private Integer maxResidents;

    private Boolean isActive;

}