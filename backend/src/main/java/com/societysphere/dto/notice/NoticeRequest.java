package com.societysphere.dto.notice;

import java.time.LocalDate;

import com.societysphere.enums.NoticeAudience;
import com.societysphere.enums.NoticePriority;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeRequest {

    @NotNull(message = "Society is required")
    private Long societyId;

    @NotNull(message = "Admin is required")
    private Long adminId;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Audience is required")
    private NoticeAudience audience;

    @NotNull(message = "Priority is required")
    private NoticePriority priority;

    private boolean active = true;

    private LocalDate expiryDate;

}