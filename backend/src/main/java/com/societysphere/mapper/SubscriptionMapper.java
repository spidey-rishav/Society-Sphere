package com.societysphere.mapper;

import com.societysphere.dto.subscription.SubscriptionRequest;
import com.societysphere.dto.subscription.SubscriptionResponse;
import com.societysphere.entity.Subscription;

public final class SubscriptionMapper {

    private SubscriptionMapper() {
    }

    public static Subscription toEntity(SubscriptionRequest request) {

        if (request == null) {
            return null;
        }

        Subscription subscription = new Subscription();

        subscription.setStartDate(request.getStartDate());

        return subscription;
    }

    public static SubscriptionResponse toResponse(Subscription subscription) {

        if (subscription == null) {
            return null;
        }

        SubscriptionResponse response = new SubscriptionResponse();

        response.setId(subscription.getId());

        if (subscription.getSociety() != null) {
            response.setSocietyId(subscription.getSociety().getId());
            response.setSocietyName(subscription.getSociety().getSocietyName());
        }

        if (subscription.getSubscriptionPlan() != null) {
            response.setSubscriptionPlanId(subscription.getSubscriptionPlan().getId());
            response.setSubscriptionPlanName(subscription.getSubscriptionPlan().getName());
            response.setPrice(subscription.getSubscriptionPlan().getPrice());
        }

        response.setStartDate(subscription.getStartDate());
        response.setEndDate(subscription.getEndDate());
        response.setStatus(subscription.getStatus());

        return response;
    }

}