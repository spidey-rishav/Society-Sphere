package com.societysphere.service;

import com.societysphere.dto.society.SocietyRequest;
import com.societysphere.dto.society.SocietyResponse;

import java.util.List;
import java.util.UUID;

public interface SocietyService {

    SocietyResponse getSocietyByPublicId(UUID publicId);

    List<SocietyResponse> getAllSocieties();

    SocietyResponse updateSociety(
            UUID publicId,
            SocietyRequest request
    );
}