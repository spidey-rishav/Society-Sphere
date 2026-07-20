package com.societysphere.repository;

import com.societysphere.entity.Flat;
import com.societysphere.entity.Resident;
import com.societysphere.entity.Society;
import com.societysphere.enums.ResidentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResidentRepository extends JpaRepository<Resident, Long> {

    // Navigate through flat -> society
    List<Resident> findByFlatSociety(Society society);

    List<Resident> findByFlat(Flat flat);

    List<Resident> findByResidentType(ResidentType residentType);

    List<Resident> findByActive(Boolean active);

    List<Resident> findByFlatSocietyAndResidentType(Society society, ResidentType residentType);

    List<Resident> findByFlatSocietyAndActive(Society society, Boolean active);

    long countByFlatSociety(Society society);

    long countByFlatSocietyAndResidentType(Society society, ResidentType residentType);

    Optional<Resident> findByUser_Id(Long userId);

    Optional<Resident> findByUser_Email(String email);

    boolean existsByUser_Email(String email);
}