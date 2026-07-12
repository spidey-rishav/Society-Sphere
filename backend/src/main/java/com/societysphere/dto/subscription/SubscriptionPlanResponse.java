package com.societysphere.mapper;

import com.societysphere.dto.subscription.SubscriptionPlanRequest;
import com.societysphere.dto.subscription.SubscriptionPlanResponse;
import com.societysphere.entity.SubscriptionPlan;

public final class SubscriptionPlanMapper {

    private SubscriptionPlanMapper() {
    }

    public static SubscriptionPlan toEntity(SubscriptionPlanRequest request) {

        if (request == null) {
            return null;
        }

        SubscriptionPlan subscriptionPlan = new SubscriptionPlan();

        subscriptionPlan.setName(request.getPlanName());
        subscriptionPlan.setDescription(request.getDescription());
        subscriptionPlan.setPrice(request.getPrice());

        if (request.getDurationInMonths() != null) {
            subscriptionPlan.setDurationInDays(request.getDurationInMonths() * 30);
        }

        subscriptionPlan.setMaxFlats(request.getMaxFlats());
        subscriptionPlan.setMaxResidents(request.getMaxResidents());

        if (request.getIsActive() != null) {
            subscriptionPlan.setActive(request.getIsActive());
        }

        return subscriptionPlan;
    }

    public static SubscriptionPlanResponse toResponse(SubscriptionPlan subscriptionPlan) {

        if (subscriptionPlan == null) {
            return null;
        }

        SubscriptionPlanResponse response = new SubscriptionPlanResponse();

        response.setId(subscriptionPlan.getId());
        response.setPlanName(subscriptionPlan.getName());
        response.setDescription(subscriptionPlan.getDescription());
        response.setPrice(subscriptionPlan.getPrice());

        if (subscriptionPlan.getDurationInDays() != null) {
            response.setDurationInMonths(subscriptionPlan.getDurationInDays() / 30);
        }

        response.setMaxFlats(subscriptionPlan.getMaxFlats());
        response.setMaxResidents(subscriptionPlan.getMaxResidents());
        response.setIsActive(subscriptionPlan.getActive());

        return response;
    }

}