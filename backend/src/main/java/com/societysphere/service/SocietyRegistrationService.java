package com.societysphere.service;

import com.societysphere.dto.society.SocietyRegistrationRequest;
import com.societysphere.dto.society.SocietyRegistrationResponse;
import com.societysphere.enums.RegistrationStatus;

import java.util.List;
import java.util.UUID;

public interface SocietyRegistrationService {

    SocietyRegistrationResponse registerSociety(
            SocietyRegistrationRequest request
    );

    SocietyRegistrationResponse getSocietyRegistrationByPublicId(
            UUID publicId
    );

    List<SocietyRegistrationResponse> getAllSocietyRegistrations();

    SocietyRegistrationResponse updateRegistrationStatus(
            UUID publicId,
            RegistrationStatus registrationStatus
    );
}