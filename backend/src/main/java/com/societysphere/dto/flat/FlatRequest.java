package com.societysphere.dto.flat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlatRequest {

    @NotNull(message = "Society is required")
    private Long societyId;

    @NotBlank(message = "Flat number is required")
    private String flatNumber;

    @NotNull(message = "Floor number is required")
    @PositiveOrZero(message = "Floor number cannot be negative")
    private Integer floorNumber;

    @NotBlank(message = "Block name is required")
    private String blockName;

}