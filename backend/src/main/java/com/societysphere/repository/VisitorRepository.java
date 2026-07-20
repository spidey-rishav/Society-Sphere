package com.societysphere.repository;

import com.societysphere.entity.Flat;
import com.societysphere.entity.SecurityGuard;
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

    // Visitor has no 'resident' field — it links to flat and society directly
    List<Visitor> findByFlat(Flat flat);

    List<Visitor> findBySociety(Society society);

    List<Visitor> findByVisitorType(VisitorType visitorType);

    List<Visitor> findByVisitorStatus(VisitorStatus visitorStatus);

    List<Visitor> findByFlatAndVisitorStatus(Flat flat, VisitorStatus visitorStatus);

    List<Visitor> findBySocietyAndVisitorStatus(Society society, VisitorStatus visitorStatus);

    List<Visitor> findBySocietyAndVisitorType(Society society, VisitorType visitorType);

    List<Visitor> findBySecurityGuard(SecurityGuard securityGuard);

    // Entry time date-based queries
    List<Visitor> findByEntryTimeBetween(java.time.LocalDateTime from, java.time.LocalDateTime to);

    long countBySociety(Society society);

    long countBySocietyAndVisitorStatus(Society society, VisitorStatus visitorStatus);
}