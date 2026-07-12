package com.societysphere.mapper;

import com.societysphere.dto.society.SocietyRequest;
import com.societysphere.dto.society.SocietyResponse;
import com.societysphere.entity.Society;

public final class SocietyMapper {

    private SocietyMapper() {
    }

    public static Society toEntity(SocietyRequest request) {

        if (request == null) {
            return null;
        }

        Society society = new Society();

        society.setSocietyName(request.getName());
        society.setAddressLine1(request.getAddress());
        society.setCity(request.getCity());
        society.setState(request.getState());
        society.setPincode(request.getPincode());

        return society;
    }

    public static SocietyResponse toResponse(Society society) {

        if (society == null) {
            return null;
        }

        SocietyResponse response = new SocietyResponse();

        response.setId(society.getId());
        response.setName(society.getSocietyName());
        response.setAddress(society.getAddressLine1());
        response.setCity(society.getCity());
        response.setState(society.getState());
        response.setPincode(society.getPincode());
        response.setIsActive(society.getActive());
        response.setTotalFlats(society.getTotalFlats());

        if (society.getSubscription() != null
                && society.getSubscription().getSubscriptionPlan() != null) {

            response.setSubscriptionPlanName(
                    society.getSubscription()
                           .getSubscriptionPlan()
                           .getPlanName()
            );
        }

        // totalResidents will be populated by the Service Layer
        // after calculating the actual resident count.

        return response;
    }
}