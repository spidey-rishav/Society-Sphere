package com.societysphere.dto.subscription;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

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
