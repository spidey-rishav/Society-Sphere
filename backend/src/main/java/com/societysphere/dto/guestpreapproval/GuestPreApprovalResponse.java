package com.societysphere.dto.guestpreapproval;

import com.societysphere.enums.GuestApprovalStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class GuestPreApprovalResponse {
    private Long id;
    private String guestName;
    private String guestMobileNumber;
    private String purpose;
    private LocalDateTime expectedArrivalTime;
    private GuestApprovalStatus approvalStatus;
    private String barcode;
    private LocalDateTime barcodeExpiryTime;
}