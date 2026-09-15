package com.societysphere.config;

import com.societysphere.entity.User;
import com.societysphere.enums.AccountStatus;
import com.societysphere.enums.UserRole;
import com.societysphere.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/** Creates the configured platform administrator once, without logging its password. */
@Component
@RequiredArgsConstructor
public class SuperAdminBootstrap implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.bootstrap.super-admin.email}")
    private String email;

    @Value("${app.bootstrap.super-admin.password}")
    private String password;

    @Value("${app.bootstrap.super-admin.reset-password:false}")
    private boolean resetPassword;

    @Override
    public void run(String... args) {
        if (!StringUtils.hasText(password)) {
            return;
        }

        String normalizedEmail = email.trim().toLowerCase();
        User existingUser = userRepository.findByEmail(normalizedEmail).orElse(null);
        if (existingUser != null) {
            if (resetPassword) {
                existingUser.setPassword(passwordEncoder.encode(password));
                existingUser.setRole(UserRole.SUPER_ADMIN);
                existingUser.setAccountStatus(AccountStatus.ACTIVE);
                existingUser.setEmailVerified(true);
                existingUser.setFirstLogin(false);
                userRepository.save(existingUser);
            }
            return;
        }

        userRepository.save(User.builder()
                .email(normalizedEmail)
                .password(passwordEncoder.encode(password))
                .role(UserRole.SUPER_ADMIN)
                .accountStatus(AccountStatus.ACTIVE)
                .emailVerified(true)
                .firstLogin(false)
                .build());
    }
}