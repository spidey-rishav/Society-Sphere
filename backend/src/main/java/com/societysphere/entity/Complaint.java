package com.societysphere.entity;

import com.societysphere.enums.ComplaintCategory;
import com.societysphere.enums.ComplaintRaisedBy;
import com.societysphere.enums.ComplaintStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "complaints",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "public_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Complaint extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "public_id", nullable = false, unique = true, updatable = false)
    private UUID publicId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "society_id", nullable = false)
    private Society society;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flat_id")
    private Flat flat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resident_id")
    private Resident resident;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "security_guard_id")
    private SecurityGuard securityGuard;

    @Enumerated(EnumType.STRING)
    @Column(name = "raised_by", nullable = false)
    private ComplaintRaisedBy raisedBy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ComplaintCategory category;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ComplaintStatus status;

    @Column(name = "payment_required", nullable = false)
    @Builder.Default
    private Boolean paymentRequired = false;

    @Column(name = "estimated_amount", precision = 10, scale = 2)
    private BigDecimal estimatedAmount;

    @Column(name = "resolved_at")
    private LocalDateTime resolvedAt;
    
    @Column(name = "admin_remarks", length = 1000)
    private String adminRemarks;

    @Column(name = "resolution_summary", length = 1000)
    private String resolutionSummary;

    @OneToOne(mappedBy = "complaint")
    private Payment payment;

    @OneToOne(mappedBy = "complaint")
    private ComplaintFeedback complaintFeedback;

    @PrePersist
    public void generatePublicId() {
        if (publicId == null) {
            publicId = UUID.randomUUID();
        }
    }
    
}