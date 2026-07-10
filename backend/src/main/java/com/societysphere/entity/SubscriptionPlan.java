package com.societysphere.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name = "subscription_plans",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "public_id"),
                @UniqueConstraint(columnNames = "name")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionPlan extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "public_id", nullable = false, unique = true, updatable = false)
    private UUID publicId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "duration_in_days", nullable = false)
    private Integer durationInDays;

    @Column(name = "max_flats", nullable = false)
    private Integer maxFlats;

    @Column(name = "max_admins", nullable = false)
    private Integer maxAdmins;

    @Column(name = "max_security_guards", nullable = false)
    private Integer maxSecurityGuards;

    @Column(name = "max_residents", nullable = false)
    private Integer maxResidents;

    @Column(name = "storage_limit_mb")
    private Integer storageLimitMb;

    @Column(nullable = false)
    private Boolean active = true;

    @OneToMany(mappedBy = "subscriptionPlan")
    private List<Subscription> subscriptions = new ArrayList<>();

    @PrePersist
    public void generatePublicId() {
        if (publicId == null) {
            publicId = UUID.randomUUID();
        }
    }
}   