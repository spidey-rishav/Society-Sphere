package com.societysphere.service;

import com.societysphere.dto.flat.FlatRequest;
import com.societysphere.dto.flat.FlatResponse;

import java.util.List;
import java.util.UUID;

public interface FlatService {

    FlatResponse createFlat(FlatRequest request);

    FlatResponse getFlatByPublicId(UUID publicId);

    List<FlatResponse> getAllFlats();

    FlatResponse updateFlat(
            UUID publicId,
            FlatRequest request
    );
}