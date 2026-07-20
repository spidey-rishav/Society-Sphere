package com.societysphere.dto.notice;

import com.societysphere.enums.NoticeAudience;
import com.societysphere.enums.NoticePriority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeResponse {
    private Long id;
    private String title;
    private String description;
    private NoticeAudience audience;
    private NoticePriority priority;
    private LocalDate expiryDate;
    private Boolean active;
    private LocalDateTime createdAt;
}