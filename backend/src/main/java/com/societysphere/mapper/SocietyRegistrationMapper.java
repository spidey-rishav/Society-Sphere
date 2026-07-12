package com.societysphere.mapper;

import com.societysphere.dto.societyregistration.SocietyRegistrationRequestDTO;
import com.societysphere.dto.societyregistration.SocietyRegistrationResponseDTO;
import com.societysphere.entity.SocietyRegistration;

public final class SocietyRegistrationMapper {

    private SocietyRegistrationMapper() {
    }

    public static SocietyRegistration toEntity(SocietyRegistrationRequestDTO requestDTO) {

        if (requestDTO == null) {
            return null;
        }

        return SocietyRegistration.builder()
                .societyName(requestDTO.getSocietyName())
                .address(requestDTO.getAddress())
                .city(requestDTO.getCity())
                .state(requestDTO.getState())
                .pincode(requestDTO.getPincode())
                .contactPersonName(requestDTO.getContactPersonName())
                .contactNumber(requestDTO.getContactNumber())
                .email(requestDTO.getEmail())
                .numberOfFlats(requestDTO.getNumberOfFlats())
                .build();
    }

    public static SocietyRegistrationResponseDTO toResponseDTO(
            SocietyRegistration registration) {

        if (registration == null) {
            return null;
        }

        return SocietyRegistrationResponseDTO.builder()
                .id(registration.getId())
                .societyName(registration.getSocietyName())
                .address(registration.getAddress())
                .city(registration.getCity())
                .state(registration.getState())
                .pincode(registration.getPincode())
                .contactPersonName(registration.getContactPersonName())
                .contactNumber(registration.getContactNumber())
                .email(registration.getEmail())
                .numberOfFlats(registration.getNumberOfFlats())
                .status(registration.getStatus())
                .createdAt(registration.getCreatedAt())
                .updatedAt(registration.getUpdatedAt())
                .build();
    }
}