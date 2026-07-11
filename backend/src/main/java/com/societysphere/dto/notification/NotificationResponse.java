package com.societysphere.dto.notification;

import java.time.LocalDateTime;

import com.societysphere.enums.NotificationPriority;
import com.societysphere.enums.NotificationStatus;
import com.societysphere.enums.NotificationType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationResponse {

    private Long notificationId;

    private Long societyId;

    private String societyName;

    private Long userId;

    private String userName;

    private NotificationType notificationType;

    private String title;

    private String message;

    private NotificationPriority priority;

    private NotificationStatus status;

    private boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}