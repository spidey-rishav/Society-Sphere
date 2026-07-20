package com.societysphere.repository;

import com.societysphere.entity.Admin;
import com.societysphere.entity.Society;
import com.societysphere.enums.AdminType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    List<Admin> findBySociety(Society society);

    List<Admin> findByAdminType(AdminType adminType);

    List<Admin> findBySocietyAndAdminType(Society society, AdminType adminType);

    long countBySociety(Society society);

    Optional<Admin> findByUser_Id(Long userId);

    Optional<Admin> findByUser_Email(String email);

    boolean existsByUser_Email(String email);
}