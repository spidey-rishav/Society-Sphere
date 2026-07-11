package com.societysphere.repository;

import com.societysphere.entity.Flat;
import com.societysphere.entity.Resident;
import com.societysphere.entity.Society;
import com.societysphere.enums.AccountStatus;
import com.societysphere.enums.ResidentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResidentRepository extends JpaRepository<Resident, Long> {

    List<Resident> findBySociety(Society society);

    List<Resident> findByFlat(Flat flat);

    List<Resident> findByResidentType(ResidentType residentType);

    List<Resident> findByAccountStatus(AccountStatus accountStatus);

    List<Resident> findBySocietyAndResidentType(
            Society society,
            ResidentType residentType
    );

    List<Resident> findBySocietyAndAccountStatus(
            Society society,
            AccountStatus accountStatus
    );

    long countBySociety(Society society);

    long countBySocietyAndResidentType(
            Society society,
            ResidentType residentType
    );

}