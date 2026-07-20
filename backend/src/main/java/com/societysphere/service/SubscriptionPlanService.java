package com.societysphere.service;

import com.societysphere.dto.subscription.SubscriptionPlanRequest;
import com.societysphere.dto.subscription.SubscriptionPlanResponse;

import java.util.List;
import java.util.UUID;

public interface SubscriptionPlanService {

    SubscriptionPlanResponse createSubscriptionPlan(
            SubscriptionPlanRequest request
    );

    SubscriptionPlanResponse getSubscriptionPlanByPublicId(
            UUID publicId
    );

    List<SubscriptionPlanResponse> getAllSubscriptionPlans();

    SubscriptionPlanResponse updateSubscriptionPlan(
            UUID publicId,
            SubscriptionPlanRequest request
    );
}