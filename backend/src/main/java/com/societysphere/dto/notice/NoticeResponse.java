package com.societysphere.dto.notice;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.societysphere.enums.NoticeAudience;
import com.societysphere.enums.NoticePriority;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeResponse {

    private Long noticeId;

    private Long societyId;

    private String societyName;

    private Long adminId;

    private String adminName;

    private String title;

    private String description;

    private NoticeAudience audience;

    private NoticePriority priority;

    private boolean active;

    private LocalDate expiryDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}