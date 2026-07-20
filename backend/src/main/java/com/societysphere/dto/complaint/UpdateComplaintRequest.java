package com.societysphere.dto.complaint;

import com.societysphere.enums.ComplaintStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateComplaintRequest {
    private ComplaintStatus status;
    private String adminRemarks;
    private String resolutionSummary;
    private Boolean paymentRequired;
    private BigDecimal estimatedAmount;
}
