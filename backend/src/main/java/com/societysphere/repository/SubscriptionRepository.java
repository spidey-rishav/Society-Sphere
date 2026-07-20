package com.societysphere.repository;

import com.societysphere.entity.Society;
import com.societysphere.entity.Subscription;
import com.societysphere.entity.SubscriptionPlan;
import com.societysphere.enums.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findBySociety(Society society);

    List<Subscription> findByStatus(SubscriptionStatus status);

    List<Subscription> findBySubscriptionPlan(SubscriptionPlan subscriptionPlan);

    List<Subscription> findByEndDateBefore(LocalDate date);

    List<Subscription> findByEndDateBetween(LocalDate startDate, LocalDate endDate);

    boolean existsBySociety(Society society);

    List<Subscription> findByStatusAndEndDateBefore(
        SubscriptionStatus status,
        LocalDate date
    );
    
}