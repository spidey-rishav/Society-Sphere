package com.societysphere.dto.subscription;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionPlanRequest {

    @NotBlank(message = "Plan name is required")
    private String planName;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private BigDecimal price;

    @NotNull(message = "Duration is required")
    @Positive(message = "Duration must be greater than zero")
    private Integer durationInMonths;

    @NotNull(message = "Maximum flats is required")
    @Positive(message = "Maximum flats must be greater than zero")
    private Integer maxFlats;

    @NotNull(message = "Maximum residents is required")
    @Positive(message = "Maximum residents must be greater than zero")
    private Integer maxResidents;

    private Boolean isActive;

}