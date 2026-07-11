package com.societysphere.repository;

import com.societysphere.entity.User;
import com.societysphere.entity.Society;
import com.societysphere.enums.AccountStatus;
import com.societysphere.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);

    List<User> findBySociety(Society society);

    List<User> findByRole(UserRole role);

    List<User> findByAccountStatus(AccountStatus accountStatus);

    List<User> findBySocietyAndRole(Society society, UserRole role);

    List<User> findBySocietyAndAccountStatus(
        Society society,
        AccountStatus accountStatus
    );

    Optional<User> findBySocietyAndEmail(
        Society society,
        String email
    );

    List<User> findBySocietyAndRoleAndAccountStatus(
        Society society,
        UserRole role,
        AccountStatus accountStatus
    );

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    long countByRole(UserRole role);

}