package com.societysphere.repository;

import com.societysphere.entity.Flat;
import com.societysphere.entity.SecurityGuard;
import com.societysphere.entity.Society;
import com.societysphere.enums.ShiftType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SecurityGuardRepository extends JpaRepository<SecurityGuard, Long> {

    List<SecurityGuard> findBySociety(Society society);

    List<SecurityGuard> findByShiftType(ShiftType shiftType);

    List<SecurityGuard> findByActive(Boolean active);

    List<SecurityGuard> findBySocietyAndShiftType(Society society, ShiftType shiftType);

    List<SecurityGuard> findBySocietyAndActive(Society society, Boolean active);

    long countBySociety(Society society);

    long countBySocietyAndShiftType(Society society, ShiftType shiftType);

    Optional<SecurityGuard> findByUser_Id(Long userId);

    Optional<SecurityGuard> findByUser_Email(String email);

    boolean existsByEmployeeId(String employeeId);
}