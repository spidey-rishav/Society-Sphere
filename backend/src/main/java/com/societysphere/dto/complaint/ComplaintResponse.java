package com.societysphere.dto.complaint;

import com.societysphere.enums.ComplaintCategory;
import com.societysphere.enums.ComplaintRaisedBy;
import com.societysphere.enums.ComplaintStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class ComplaintResponse {
    private Long id;
    private UUID publicId;
    private String title;
    private String description;
    private ComplaintCategory category;
    private ComplaintStatus status;
    private ComplaintRaisedBy raisedBy;
    private Boolean paymentRequired;
    private BigDecimal estimatedAmount;
    private String adminRemarks;
    private String resolutionSummary;
    private LocalDateTime resolvedAt;
    private LocalDateTime createdAt;
    private String flatNumber;
    private String blockName;
    private String raisedByName;
}