package com.societysphere.entity;

import com.societysphere.enums.GuestApprovalStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "guest_pre_approvals",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "public_id"),
                @UniqueConstraint(columnNames = "barcode")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuestPreApproval extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "public_id", nullable = false, unique = true, updatable = false)
    private UUID publicId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resident_id", nullable = false)
    private Resident resident;

    @Column(name = "guest_name", nullable = false, length = 100)
    private String guestName;

    @Column(name = "guest_mobile_number", nullable = false, length = 15)
    private String guestMobileNumber;

    @Column(nullable = false, length = 200)
    private String purpose;

    @Column(name = "expected_arrival_time", nullable = false)
    private LocalDateTime expectedArrivalTime;

    @Column(nullable = false, unique = true)
    private String barcode;

    @Enumerated(EnumType.STRING)
    @Column(name = "approval_status", nullable = false)
    private GuestApprovalStatus approvalStatus;

    @Column(name = "barcode_generated_at")
    private LocalDateTime barcodeGeneratedAt;

    @Column(name = "barcode_expiry_time")
    private LocalDateTime barcodeExpiryTime;

    @PrePersist
    public void generatePublicId() {
        if (publicId == null) {
            publicId = UUID.randomUUID();
        }
    }
}   