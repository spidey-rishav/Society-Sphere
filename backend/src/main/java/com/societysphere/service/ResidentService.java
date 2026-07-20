package com.societysphere.service;

import com.societysphere.dto.resident.ResidentRequest;
import com.societysphere.dto.resident.ResidentResponse;

import java.util.List;
import java.util.UUID;

public interface ResidentService {

    ResidentResponse createResident(ResidentRequest request);

    ResidentResponse getResidentByPublicId(UUID publicId);

    List<ResidentResponse> getAllResidents();

    ResidentResponse updateResident(
            UUID publicId,
            ResidentRequest request
    );
}