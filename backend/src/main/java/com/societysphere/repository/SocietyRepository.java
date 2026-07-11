package com.societysphere.repository;

import com.societysphere.entity.Society;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SocietyRepository extends JpaRepository<Society, Long> {

    Optional<Society> findBySocietyCode(String societyCode);

    Optional<Society> findByEmail(String email);

    boolean existsBySocietyCode(String societyCode);

    boolean existsByEmail(String email);

    boolean existsBySocietyName(String societyName);

}