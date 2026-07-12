package com.societysphere.mapper;

import com.societysphere.dto.notification.NotificationRequest;
import com.societysphere.dto.notification.NotificationResponse;
import com.societysphere.entity.Notification;

public final class NotificationMapper {

    private NotificationMapper() {
    }

    public static Notification toEntity(NotificationRequest request) {

        if (request == null) {
            return null;
        }

        Notification notification = new Notification();

        notification.setNotificationType(request.getNotificationType());
        notification.setTitle(request.getTitle());
        notification.setMessage(request.getMessage());
        notification.setPriority(request.getPriority());
        notification.setActive(request.isActive());

        return notification;
    }

    public static NotificationResponse toResponse(Notification notification) {

        if (notification == null) {
            return null;
        }

        NotificationResponse response = new NotificationResponse();

        response.setNotificationId(notification.getNotificationId());

        if (notification.getSociety() != null) {
            response.setSocietyId(notification.getSociety().getId());
            response.setSocietyName(notification.getSociety().getSocietyName());
        }

        if (notification.getUser() != null) {
            response.setUserId(notification.getUser().getId());

            /*
             * User.java does not contain a fullName field.
             * Therefore, userName cannot be populated here.
             * It should be populated by the Service Layer if required.
             */
        }

        response.setNotificationType(notification.getNotificationType());
        response.setTitle(notification.getTitle());
        response.setMessage(notification.getMessage());
        response.setPriority(notification.getPriority());
        response.setStatus(notification.getStatus());
        response.setActive(notification.isActive());

        response.setCreatedAt(notification.getCreatedAt());
        response.setUpdatedAt(notification.getUpdatedAt());

        return response;
    }

}