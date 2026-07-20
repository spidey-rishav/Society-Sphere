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
                .societyAddress(requestDTO.getSocietyAddress())
                .city(requestDTO.getCity())
                .state(requestDTO.getState())
                .pincode(requestDTO.getPincode())
                .contactPersonName(requestDTO.getContactPersonName())
                .contactNumber(requestDTO.getContactNumber())
                .email(requestDTO.getEmail())
                .build();
    }

    public static SocietyRegistrationResponseDTO toResponseDTO(
            SocietyRegistration registration) {

        if (registration == null) {
            return null;
        }

        return SocietyRegistrationResponseDTO.builder()
                .id(registration.getRegistrationId())
                .societyName(registration.getSocietyName())
                .societyAddress(registration.getSocietyAddress())
                .city(registration.getCity())
                .state(registration.getState())
                .pincode(registration.getPincode())
                .contactPersonName(registration.getContactPersonName())
                .contactNumber(registration.getContactNumber())
                .email(registration.getEmail())
                .status(registration.getRegistrationStatus())
                .createdAt(registration.getCreatedAt())
                .updatedAt(registration.getUpdatedAt())
                .build();
    }
}