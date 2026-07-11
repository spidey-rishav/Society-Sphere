package com.societysphere.repository;

import com.societysphere.entity.Notification;
import com.societysphere.entity.User;
import com.societysphere.enums.NotificationPriority;
import com.societysphere.enums.NotificationStatus;
import com.societysphere.enums.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUser(User user);

    List<Notification> findByNotificationStatus(
            NotificationStatus notificationStatus
    );

    List<Notification> findByNotificationType(
            NotificationType notificationType
    );

    List<Notification> findByNotificationPriority(
            NotificationPriority notificationPriority
    );

    List<Notification> findByUserAndNotificationStatus(
            User user,
            NotificationStatus notificationStatus
    );

    List<Notification> findByUserAndNotificationType(
            User user,
            NotificationType notificationType
    );

    long countByUser(User user);

    long countByUserAndNotificationStatus(
            User user,
            NotificationStatus notificationStatus
    );

}