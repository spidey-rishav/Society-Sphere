package com.societysphere.dto.complaint;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.societysphere.enums.ComplaintCategory;
import com.societysphere.enums.ComplaintRaisedBy;
import com.societysphere.enums.ComplaintStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComplaintResponse {

    private Long id;

    private UUID publicId;

    private Long societyId;

    private String societyName;

    private Long flatId;

    private String flatNumber;

    private Long residentId;

    private String residentName;

    private Long securityGuardId;

    private String securityGuardName;

    private ComplaintRaisedBy raisedBy;

    private ComplaintCategory category;

    private String title;

    private String description;

    private ComplaintStatus status;

    private Boolean paymentRequired;

    private BigDecimal estimatedAmount;

    private LocalDateTime resolvedAt;

    private String adminRemarks;

    private String resolutionSummary;

}