package com.societysphere.dto.notice;

import com.societysphere.enums.NoticeAudience;
import com.societysphere.enums.NoticePriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateNoticeRequest {

    @NotBlank(message = "Title is required")
    @Size(max = 150, message = "Title cannot exceed 150 characters")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Audience is required")
    private NoticeAudience audience;

    @NotNull(message = "Priority is required")
    private NoticePriority priority;

    private LocalDate expiryDate;
}
