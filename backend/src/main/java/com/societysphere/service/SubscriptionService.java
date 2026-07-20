package com.societysphere.service;

import com.societysphere.dto.subscription.SubscriptionRequest;
import com.societysphere.dto.subscription.SubscriptionResponse;

import java.util.List;
import java.util.UUID;

public interface SubscriptionService {

    SubscriptionResponse createSubscription(
            SubscriptionRequest request
    );

    SubscriptionResponse getSubscriptionByPublicId(
            UUID publicId
    );

    List<SubscriptionResponse> getAllSubscriptions();

    SubscriptionResponse updateSubscription(
            UUID publicId,
            SubscriptionRequest request
    );
}