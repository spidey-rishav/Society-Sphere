package com.societysphere.repository;

import com.societysphere.entity.SecurityGuard;
import com.societysphere.entity.Society;
import com.societysphere.enums.AccountStatus;
import com.societysphere.enums.ShiftType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecurityGuardRepository extends JpaRepository<SecurityGuard, Long> {

    List<SecurityGuard> findBySociety(Society society);

    List<SecurityGuard> findByShiftType(ShiftType shiftType);

    List<SecurityGuard> findByAccountStatus(AccountStatus accountStatus);

    List<SecurityGuard> findBySocietyAndShiftType(
            Society society,
            ShiftType shiftType
    );

    List<SecurityGuard> findBySocietyAndAccountStatus(
            Society society,
            AccountStatus accountStatus
    );

    long countBySociety(Society society);

    long countBySocietyAndShiftType(
            Society society,
            ShiftType shiftType
    );

}