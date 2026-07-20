package com.societysphere.repository;

import com.societysphere.entity.Complaint;
import com.societysphere.entity.Resident;
import com.societysphere.entity.SecurityGuard;
import com.societysphere.entity.Society;
import com.societysphere.enums.ComplaintCategory;
import com.societysphere.enums.ComplaintRaisedBy;
import com.societysphere.enums.ComplaintStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    List<Complaint> findByResident(Resident resident);

    List<Complaint> findBySociety(Society society);

    // Complaint entity has 'status' field, not 'complaintStatus'
    List<Complaint> findByStatus(ComplaintStatus status);

    List<Complaint> findByCategory(ComplaintCategory category);

    List<Complaint> findByRaisedBy(ComplaintRaisedBy raisedBy);

    List<Complaint> findByResidentAndStatus(Resident resident, ComplaintStatus status);

    List<Complaint> findBySocietyAndStatus(Society society, ComplaintStatus status);

    List<Complaint> findBySocietyAndRaisedBy(Society society, ComplaintRaisedBy raisedBy);

    List<Complaint> findBySecurityGuard(SecurityGuard securityGuard);

    List<Complaint> findBySecurityGuardAndStatus(SecurityGuard securityGuard, ComplaintStatus status);

    long countBySociety(Society society);

    long countBySocietyAndStatus(Society society, ComplaintStatus status);
}