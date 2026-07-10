package com.societysphere.entity;

import com.societysphere.enums.FlatOccupancyStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name = "flats",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "public_id"),
                @UniqueConstraint(columnNames = {"society_id", "block", "flat_number"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Flat extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "public_id", nullable = false, unique = true, updatable = false)
    private UUID publicId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "society_id", nullable = false)
    private Society society;

    @Column(nullable = false, length = 20)
    private String block;

    @Column(name = "floor_number", nullable = false)
    private Integer floorNumber;

    @Column(name = "flat_number", nullable = false, length = 20)
    private String flatNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "occupancy_status", nullable = false)
    private FlatOccupancyStatus occupancyStatus;

    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;

    @OneToMany(mappedBy = "flat")
    @Builder.Default
    private List<Resident> residents = new ArrayList<>();

    @OneToMany(mappedBy = "flat")
    @Builder.Default
    private List<Visitor> visitors = new ArrayList<>();

    @OneToMany(mappedBy = "flat")
    @Builder.Default
    private List<Complaint> complaints = new ArrayList<>();

    @PrePersist
    public void generatePublicId() {
        if (publicId == null) {
            publicId = UUID.randomUUID();
        }
    }
}