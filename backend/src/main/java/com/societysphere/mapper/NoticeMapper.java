package com.societysphere.mapper;

import com.societysphere.dto.notice.NoticeRequest;
import com.societysphere.dto.notice.NoticeResponse;
import com.societysphere.entity.Notice;

public final class NoticeMapper {

    private NoticeMapper() {
    }

    public static Notice toEntity(NoticeRequest request) {

        if (request == null) {
            return null;
        }

        Notice notice = new Notice();

        notice.setTitle(request.getTitle());
        notice.setDescription(request.getDescription());
        notice.setAudience(request.getAudience());
        notice.setPriority(request.getPriority());
        notice.setActive(request.isActive());
        notice.setExpiryDate(request.getExpiryDate());

        return notice;
    }

    public static NoticeResponse toResponse(Notice notice) {

        if (notice == null) {
            return null;
        }

        NoticeResponse response = new NoticeResponse();

        response.setNoticeId(notice.getNoticeId());

        if (notice.getSociety() != null) {
            response.setSocietyId(notice.getSociety().getId());
            response.setSocietyName(notice.getSociety().getSocietyName());
        }

        if (notice.getAdmin() != null) {
            response.setAdminId(notice.getAdmin().getId());
            response.setAdminName(notice.getAdmin().getFullName());
        }

        response.setTitle(notice.getTitle());
        response.setDescription(notice.getDescription());
        response.setAudience(notice.getAudience());
        response.setPriority(notice.getPriority());
        response.setActive(notice.isActive());
        response.setExpiryDate(notice.getExpiryDate());

        response.setCreatedAt(notice.getCreatedAt());
        response.setUpdatedAt(notice.getUpdatedAt());

        return response;
    }

}