package com.societysphere.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name = "societies",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "public_id"),
                @UniqueConstraint(columnNames = "society_code"),
                @UniqueConstraint(columnNames = "registration_number")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Society extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "public_id", nullable = false, unique = true, updatable = false)
    private UUID publicId;

    @Column(name = "society_name", nullable = false, length = 150)
    private String societyName;

    @Column(name = "society_code", nullable = false, unique = true, length = 20)
    private String societyCode;

    @Column(name = "registration_number", nullable = false, unique = true)
    private String registrationNumber;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "address_line_1", nullable = false)
    private String addressLine1;

    @Column(name = "address_line_2")
    private String addressLine2;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String pincode;

    @Column(name = "logo")
    private String logo;

    @Column(name = "total_flats", nullable = false)
    private Integer totalFlats;

    @Column(nullable = false)
    private Boolean active = true;

    @OneToOne(mappedBy = "society")
    private Subscription subscription;

    @OneToMany(mappedBy = "society")
    private List<Admin> admins = new ArrayList<>();

    @OneToMany(mappedBy = "society")
    private List<Flat> flats = new ArrayList<>();

    @OneToMany(mappedBy = "society")
    private List<SecurityGuard> securityGuards = new ArrayList<>();

    @OneToMany(mappedBy = "society")
    private List<Visitor> visitors = new ArrayList<>();

    @OneToMany(mappedBy = "society")
    private List<Complaint> complaints = new ArrayList<>();

    @OneToMany(mappedBy = "society")
    private List<Notice> notices = new ArrayList<>();

    @PrePersist
    public void generatePublicId() {
        if (publicId == null) {
            publicId = UUID.randomUUID();
        }
    }
}