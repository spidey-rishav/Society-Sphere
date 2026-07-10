package com.societysphere.entity;

import com.societysphere.enums.RegistrationStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "society_registrations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocietyRegistration extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long registrationId;

    @Column(nullable = false, length = 150)
    private String societyName;

    @Column(nullable = false, length = 255)
    private String societyAddress;

    @Column(nullable = false, length = 100)
    private String city;

    @Column(nullable = false, length = 100)
    private String state;

    @Column(nullable = false, length = 10)
    private String pincode;

    @Column(nullable = false, length = 100)
    private String contactPersonName;

    @Column(nullable = false, length = 15)
    private String contactNumber;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false)
    private String identityProof;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscription_plan_id", nullable = false)
    private SubscriptionPlan subscriptionPlan;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RegistrationStatus registrationStatus;

    @Column(nullable = false)
    @Builder.Default
    private boolean accountCreated = false;

    @Column(length = 500)
    private String remarks;

}