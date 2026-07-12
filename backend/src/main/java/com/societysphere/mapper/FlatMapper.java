package com.societysphere.mapper;

import com.societysphere.dto.flat.FlatRequest;
import com.societysphere.dto.flat.FlatResponse;
import com.societysphere.entity.Flat;
import com.societysphere.enums.FlatOccupancyStatus;

public final class FlatMapper {

    private FlatMapper() {
    }

    public static Flat toEntity(FlatRequest request) {

        if (request == null) {
            return null;
        }

        Flat flat = new Flat();

        flat.setFlatNumber(request.getFlatNumber());
        flat.setFloorNumber(request.getFloorNumber());
        flat.setBlock(request.getBlockName());

        return flat;
    }

    public static FlatResponse toResponse(Flat flat) {

        if (flat == null) {
            return null;
        }

        FlatResponse response = new FlatResponse();

        response.setId(flat.getId());

        if (flat.getSociety() != null) {
            response.setSocietyId(flat.getSociety().getId());
            response.setSocietyName(flat.getSociety().getSocietyName());
        }

        response.setFlatNumber(flat.getFlatNumber());
        response.setFloorNumber(flat.getFloorNumber());
        response.setBlockName(flat.getBlock());

        response.setIsOccupied(
                flat.getOccupancyStatus() == FlatOccupancyStatus.OCCUPIED
        );

        return response;
    }

}