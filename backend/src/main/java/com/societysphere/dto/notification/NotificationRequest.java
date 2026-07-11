package com.societysphere.dto.notification;

import com.societysphere.enums.NotificationPriority;
import com.societysphere.enums.NotificationType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationRequest {

    @NotNull(message = "Society is required")
    private Long societyId;

    @NotNull(message = "User is required")
    private Long userId;

    @NotNull(message = "Notification type is required")
    private NotificationType notificationType;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Message is required")
    private String message;

    @NotNull(message = "Priority is required")
    private NotificationPriority priority;

    private boolean active = true;

}