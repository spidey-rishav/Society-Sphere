package com.societysphere.repository;

import com.societysphere.entity.Flat;
import com.societysphere.entity.Society;
import com.societysphere.enums.FlatOccupancyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlatRepository extends JpaRepository<Flat, Long> {

    Optional<Flat> findByFlatNumberAndSociety(
            String flatNumber,
            Society society
    );

    List<Flat> findBySociety(Society society);

    List<Flat> findByOccupancyStatus(
            FlatOccupancyStatus occupancyStatus
    );

    List<Flat> findBySocietyAndOccupancyStatus(
            Society society,
            FlatOccupancyStatus occupancyStatus
    );

    boolean existsByFlatNumberAndSociety(
            String flatNumber,
            Society society
    );

    long countBySociety(Society society);

    long countBySocietyAndOccupancyStatus(
            Society society,
            FlatOccupancyStatus occupancyStatus
    );

}