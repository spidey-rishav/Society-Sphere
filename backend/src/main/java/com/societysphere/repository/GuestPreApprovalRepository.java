package com.societysphere.repository;

import com.societysphere.entity.GuestPreApproval;
import com.societysphere.entity.Resident;
import com.societysphere.entity.Society;
import com.societysphere.enums.GuestApprovalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface GuestPreApprovalRepository extends JpaRepository<GuestPreApproval, Long> {

    List<GuestPreApproval> findByResident(Resident resident);

    List<GuestPreApproval> findBySociety(Society society);

    List<GuestPreApproval> findByApprovalStatus(
            GuestApprovalStatus approvalStatus
    );

    List<GuestPreApproval> findByResidentAndApprovalStatus(
            Resident resident,
            GuestApprovalStatus approvalStatus
    );

    List<GuestPreApproval> findByVisitDate(LocalDate visitDate);

    List<GuestPreApproval> findByResidentAndVisitDate(
            Resident resident,
            LocalDate visitDate
    );

}