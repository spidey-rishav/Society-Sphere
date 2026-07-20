package com.societysphere.repository;

import com.societysphere.entity.Complaint;
import com.societysphere.entity.Payment;
import com.societysphere.entity.Society;
import com.societysphere.entity.Subscription;
import com.societysphere.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {


    Optional<Payment> findByComplaint(Complaint complaint);

    List<Payment> findBySociety(Society society);

    List<Payment> findByPaymentStatus(PaymentStatus paymentStatus);

    List<Payment> findBySocietyAndPaymentStatus(
            Society society,
            PaymentStatus paymentStatus
    );

    long countBySociety(Society society);

    long countBySocietyAndPaymentStatus(
            Society society,
            PaymentStatus paymentStatus
    );

}