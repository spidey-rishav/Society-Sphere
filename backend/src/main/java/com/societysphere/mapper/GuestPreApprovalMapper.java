package com.societysphere.mapper;

import com.societysphere.dto.guestpreapproval.GuestPreApprovalRequest;
import com.societysphere.dto.guestpreapproval.GuestPreApprovalResponse;
import com.societysphere.entity.GuestPreApproval;

public final class GuestPreApprovalMapper {

    private GuestPreApprovalMapper() {
    }

    public static GuestPreApproval toEntity(GuestPreApprovalRequest request) {

        if (request == null) {
            return null;
        }

        GuestPreApproval guestPreApproval = new GuestPreApproval();

        guestPreApproval.setGuestName(request.getGuestName());
        guestPreApproval.setGuestMobileNumber(request.getGuestMobileNumber());
        guestPreApproval.setPurpose(request.getPurpose());

        /*
         * visitDate is intentionally not mapped because the entity stores
         * expectedArrivalTime (LocalDateTime), whereas the DTO contains
         * visitDate (LocalDate). The Service Layer should combine the date
         * with the desired arrival time before persisting.
         */

        return guestPreApproval;
    }

    public static GuestPreApprovalResponse toResponse(GuestPreApproval guestPreApproval) {

        if (guestPreApproval == null) {
            return null;
        }

        return GuestPreApprovalResponse.builder()
                .id(guestPreApproval.getId())
                .guestName(guestPreApproval.getGuestName())
                .guestMobileNumber(guestPreApproval.getGuestMobileNumber())
                .purpose(guestPreApproval.getPurpose())
                .expectedArrivalTime(guestPreApproval.getExpectedArrivalTime())
                .approvalStatus(guestPreApproval.getApprovalStatus())
                .build();
    }

}