package com.societysphere.dto.complaint;

import com.societysphere.enums.ComplaintCategory;
import com.societysphere.enums.ComplaintRaisedBy;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComplaintRequest {

    @NotNull(message = "Society is required")
    private Long societyId;

    private Long flatId;

    private Long residentId;

    private Long securityGuardId;

    @NotNull(message = "Raised by is required")
    private ComplaintRaisedBy raisedBy;

    @NotNull(message = "Complaint category is required")
    private ComplaintCategory category;

    @NotBlank(message = "Complaint title is required")
    private String title;

    @NotBlank(message = "Complaint description is required")
    private String description;

}