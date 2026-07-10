package com.societysphere.entity;

import com.societysphere.enums.NoticeAudience;
import com.societysphere.enums.NoticePriority;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "notices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "society_id", nullable = false)
    private Society society;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NoticeAudience audience;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NoticePriority priority;

    @Column(nullable = false)
    @Builder.Default
    private boolean active = true;
        
    @Column
    private LocalDate expiryDate;

}