package com.societysphere.repository;

import com.societysphere.entity.Resident;
import com.societysphere.entity.Society;
import com.societysphere.entity.Visitor;
import com.societysphere.enums.VisitorStatus;
import com.societysphere.enums.VisitorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Long> {

    List<Visitor> findByResident(Resident resident);

    List<Visitor> findBySociety(Society society);

    List<Visitor> findByVisitorType(VisitorType visitorType);

    List<Visitor> findByVisitorStatus(VisitorStatus visitorStatus);

    List<Visitor> findByResidentAndVisitorStatus(
            Resident resident,
            VisitorStatus visitorStatus
    );

    List<Visitor> findBySocietyAndVisitorStatus(
            Society society,
            VisitorStatus visitorStatus
    );

    List<Visitor> findByEntryDate(LocalDate entryDate);

    long countBySociety(Society society);

    long countBySocietyAndVisitorStatus(
            Society society,
            VisitorStatus visitorStatus
    );

}