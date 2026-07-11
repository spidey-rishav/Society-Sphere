package com.societysphere.dto.admin;

import com.societysphere.enums.AdminType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminRequest {

    @NotNull(message = "User is required")
    private Long userId;

    @NotNull(message = "Society is required")
    private Long societyId;

    @NotNull(message = "Admin type is required")
    private AdminType adminType;

    @NotBlank(message = "Designation is required")
    private String designation;

}