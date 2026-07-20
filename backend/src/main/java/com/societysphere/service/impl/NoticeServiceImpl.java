package com.societysphere.service.impl;

import com.societysphere.dto.notice.CreateNoticeRequest;
import com.societysphere.dto.notice.NoticeResponse;
import com.societysphere.entity.Admin;
import com.societysphere.entity.Notice;
import com.societysphere.entity.Society;
import com.societysphere.enums.NoticeAudience;
import com.societysphere.repository.AdminRepository;
import com.societysphere.repository.NoticeRepository;
import com.societysphere.repository.SocietyRepository;
import com.societysphere.response.ApiResponse;
import com.societysphere.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;
    private final AdminRepository adminRepository;
    private final SocietyRepository societyRepository;

    @Override
    @Transactional
    public ApiResponse<NoticeResponse> createNotice(CreateNoticeRequest request, Long adminId, Long societyId) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        Society society = societyRepository.findById(societyId)
                .orElseThrow(() -> new RuntimeException("Society not found"));

        Notice notice = Notice.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .audience(request.getAudience())
                .priority(request.getPriority())
                .expiryDate(request.getExpiryDate())
                .admin(admin)
                .society(society)
                .active(true)
                .build();

        notice = noticeRepository.save(notice);
        return ApiResponse.success("Notice created successfully", mapToResponse(notice));
    }

    @Override
    public ApiResponse<List<NoticeResponse>> getNoticesForSociety(Long societyId) {
        List<Notice> notices = noticeRepository.findAll().stream()
                .filter(n -> n.getSociety().getId().equals(societyId) && n.isActive())
                .collect(Collectors.toList());
        List<NoticeResponse> responses = notices.stream().map(this::mapToResponse).collect(Collectors.toList());
        return ApiResponse.success("Notices fetched successfully", responses);
    }

    @Override
    public ApiResponse<List<NoticeResponse>> getNoticesForAudience(Long societyId, NoticeAudience audience) {
        List<Notice> notices = noticeRepository.findAll().stream()
                .filter(n -> n.getSociety().getId().equals(societyId) && n.isActive() && 
                        (n.getAudience() == audience || n.getAudience() == NoticeAudience.ALL))
                .collect(Collectors.toList());
        List<NoticeResponse> responses = notices.stream().map(this::mapToResponse).collect(Collectors.toList());
        return ApiResponse.success("Notices fetched successfully", responses);
    }

    @Override
    @Transactional
    public ApiResponse<NoticeResponse> updateNotice(Long noticeId, CreateNoticeRequest request) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new RuntimeException("Notice not found"));
        
        notice.setTitle(request.getTitle());
        notice.setDescription(request.getDescription());
        notice.setAudience(request.getAudience());
        notice.setPriority(request.getPriority());
        notice.setExpiryDate(request.getExpiryDate());

        notice = noticeRepository.save(notice);
        return ApiResponse.success("Notice updated successfully", mapToResponse(notice));
    }

    @Override
    @Transactional
    public ApiResponse<String> deleteNotice(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new RuntimeException("Notice not found"));
        notice.setActive(false);
        noticeRepository.save(notice);
        return ApiResponse.success("Notice deleted successfully", null);
    }

    private NoticeResponse mapToResponse(Notice notice) {
        return NoticeResponse.builder()
                .id(notice.getNoticeId())
                .title(notice.getTitle())
                .description(notice.getDescription())
                .audience(notice.getAudience())
                .priority(notice.getPriority())
                .expiryDate(notice.getExpiryDate())
                .active(notice.isActive())
                .createdAt(notice.getCreatedAt())
                .build();
    }
}
