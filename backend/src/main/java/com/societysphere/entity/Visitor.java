package com.societysphere.entity;

import com.societysphere.enums.VehicleType;
import com.societysphere.enums.VisitorStatus;
import com.societysphere.enums.VisitorType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "visitors",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "public_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Visitor extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "public_id", nullable = false, unique = true, updatable = false)
    private UUID publicId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "society_id", nullable = false)
    private Society society;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flat_id", nullable = false)
    private Flat flat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "security_guard_id", nullable = false)
    private SecurityGuard securityGuard;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guest_pre_approval_id")
    private GuestPreApproval guestPreApproval;

    @Enumerated(EnumType.STRING)
    @Column(name = "visitor_type", nullable = false)
    private VisitorType visitorType;

    @Column(name = "visitor_name", nullable = false, length = 100)
    private String visitorName;

    @Column(name = "mobile_number", length = 15)
    private String mobileNumber;

    @Column(name = "company_name")
    private String companyName;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type")
    private VehicleType vehicleType;

    @Column(name = "vehicle_number", length = 20)
    private String vehicleNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "visitor_status", nullable = false)
    private VisitorStatus visitorStatus;

    @Column(name = "entry_time")
    private LocalDateTime entryTime;

    @Column(name = "exit_time")
    private LocalDateTime exitTime;

    @PrePersist
    public void generatePublicId() {
        if (publicId == null) {
            publicId = UUID.randomUUID();
        }
    }
}   