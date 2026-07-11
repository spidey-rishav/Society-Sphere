package com.societysphere.repository;

import com.societysphere.entity.SocietyRegistration;
import com.societysphere.enums.RegistrationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SocietyRegistrationRepository extends JpaRepository<SocietyRegistration, Long> {

    Optional<SocietyRegistration> findByEmail(String email);

    boolean existsByEmail(String email);

    List<SocietyRegistration> findByRegistrationStatus(RegistrationStatus registrationStatus);

    List<SocietyRegistration> findByAccountCreated(boolean accountCreated);

    List<SocietyRegistration> findByRegistrationStatusAndAccountCreated(
        RegistrationStatus registrationStatus,
        boolean accountCreated
    );

}