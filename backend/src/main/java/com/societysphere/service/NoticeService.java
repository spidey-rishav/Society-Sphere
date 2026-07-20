package com.societysphere.service;

import com.societysphere.dto.notice.CreateNoticeRequest;
import com.societysphere.dto.notice.NoticeResponse;
import com.societysphere.enums.NoticeAudience;
import com.societysphere.response.ApiResponse;

import java.util.List;

public interface NoticeService {
    ApiResponse<NoticeResponse> createNotice(CreateNoticeRequest request, Long adminId, Long societyId);
    ApiResponse<List<NoticeResponse>> getNoticesForSociety(Long societyId);
    ApiResponse<List<NoticeResponse>> getNoticesForAudience(Long societyId, NoticeAudience audience);
    ApiResponse<NoticeResponse> updateNotice(Long noticeId, CreateNoticeRequest request);
    ApiResponse<String> deleteNotice(Long noticeId);
}
