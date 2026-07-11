package com.societysphere.dto.guestpreapproval;

import java.time.LocalDate;

import com.societysphere.enums.GuestApprovalStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuestPreApprovalResponse {

    private Long id;

    private Long residentId;

    private String residentName;

    private String flatNumber;

    private String guestName;

    private String guestMobileNumber;

    private LocalDate visitDate;

    private String purpose;

    private GuestApprovalStatus status;

}