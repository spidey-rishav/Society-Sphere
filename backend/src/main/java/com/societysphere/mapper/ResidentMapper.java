package com.societysphere.mapper;

import com.societysphere.dto.resident.ResidentRequest;
import com.societysphere.dto.resident.ResidentResponse;
import com.societysphere.entity.Resident;

public final class ResidentMapper {

    private ResidentMapper() {
    }

    public static Resident toEntity(ResidentRequest request) {

        if (request == null) {
            return null;
        }

        Resident resident = new Resident();

        resident.setResidentType(request.getResidentType());
        resident.setGender(request.getGender());
        resident.setDateOfBirth(request.getDateOfBirth());

        return resident;
    }

    public static ResidentResponse toResponse(Resident resident) {

        if (resident == null) {
            return null;
        }

        ResidentResponse response = new ResidentResponse();

        response.setId(resident.getId());

        if (resident.getUser() != null) {
            response.setEmail(resident.getUser().getEmail());
        }

        response.setFullName(resident.getFullName());
        response.setMobileNumber(resident.getMobileNumber());

        if (resident.getFlat() != null) {
            response.setFlatNumber(resident.getFlat().getFlatNumber());
            response.setBlockName(resident.getFlat().getBlock());

            if (resident.getFlat().getSociety() != null) {
                response.setSocietyName(resident.getFlat().getSociety().getSocietyName());
            }
        }

        response.setResidentType(resident.getResidentType());
        response.setGender(resident.getGender());
        response.setDateOfBirth(resident.getDateOfBirth());

        // Move-in date is not available in Resident.java.
        // It should be populated by the Service Layer if added in future.

        return response;
    }

}