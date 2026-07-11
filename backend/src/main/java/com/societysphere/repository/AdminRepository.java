package com.societysphere.repository;

import com.societysphere.entity.Admin;
import com.societysphere.entity.Society;
import com.societysphere.enums.AccountStatus;
import com.societysphere.enums.AdminType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    List<Admin> findBySociety(Society society);

    List<Admin> findByAdminType(AdminType adminType);

    List<Admin> findByAccountStatus(AccountStatus accountStatus);

    List<Admin> findBySocietyAndAdminType(
            Society society,
            AdminType adminType
    );

    List<Admin> findBySocietyAndAccountStatus(
            Society society,
            AccountStatus accountStatus
    );

    List<Admin> findBySocietyAndAdminTypeAndAccountStatus(
            Society society,
            AdminType adminType,
            AccountStatus accountStatus
    );

    long countBySociety(Society society);

    long countBySocietyAndAccountStatus(
            Society society,
            AccountStatus accountStatus
    );

}