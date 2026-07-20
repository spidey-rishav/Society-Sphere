package com.societysphere.repository;

import com.societysphere.entity.GuestPreApproval;
import com.societysphere.entity.Resident;
import com.societysphere.enums.GuestApprovalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface GuestPreApprovalRepository extends JpaRepository<GuestPreApproval, Long> {

    List<GuestPreApproval> findByResident(Resident resident);

    // GuestPreApproval has no direct 'society' field
    // Navigate through resident -> flat -> society if needed via @Query

    List<GuestPreApproval> findByApprovalStatus(GuestApprovalStatus approvalStatus);

    List<GuestPreApproval> findByResidentAndApprovalStatus(Resident resident, GuestApprovalStatus approvalStatus);

    // GuestPreApproval has 'expectedArrivalTime' not 'visitDate'
    List<GuestPreApproval> findByExpectedArrivalTimeBetween(LocalDateTime from, LocalDateTime to);

    Optional<GuestPreApproval> findByBarcode(String barcode);

    boolean existsByBarcode(String barcode);
}