package com.societysphere.repository;

import com.societysphere.entity.Complaint;
import com.societysphere.entity.Resident;
import com.societysphere.entity.Society;
import com.societysphere.enums.ComplaintCategory;
import com.societysphere.enums.ComplaintStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    List<Complaint> findByResident(Resident resident);

    List<Complaint> findBySociety(Society society);

    List<Complaint> findByComplaintStatus(
            ComplaintStatus complaintStatus
    );

    List<Complaint> findByComplaintCategory(
            ComplaintCategory complaintCategory
    );

    List<Complaint> findByResidentAndComplaintStatus(
            Resident resident,
            ComplaintStatus complaintStatus
    );

    List<Complaint> findBySocietyAndComplaintStatus(
            Society society,
            ComplaintStatus complaintStatus
    );

    long countBySociety(Society society);

    long countBySocietyAndComplaintStatus(
            Society society,
            ComplaintStatus complaintStatus
    );

}