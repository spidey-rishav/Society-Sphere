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

    List<Notification> findByStatus(
            NotificationStatus status
    );

    List<Notification> findByNotificationType(
            NotificationType notificationType
    );

    List<Notification> findByPriority(
            NotificationPriority priority
    );

    List<Notification> findByUserAndStatus(
            User user,
            NotificationStatus status
    );

    List<Notification> findByUserAndNotificationType(
            User user,
            NotificationType notificationType
    );

    long countByUser(User user);

    long countByUserAndStatus(
            User user,
            NotificationStatus status
    );

}