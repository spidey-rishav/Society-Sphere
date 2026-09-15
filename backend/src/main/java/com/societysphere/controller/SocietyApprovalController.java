package com.societysphere.controller;

import com.societysphere.entity.Admin;
import com.societysphere.entity.Society;
import com.societysphere.entity.User;
import com.societysphere.enums.AccountStatus;
import com.societysphere.enums.AdminType;
import com.societysphere.enums.RegistrationStatus;
import com.societysphere.enums.UserRole;
import com.societysphere.exception.BadRequestException;
import com.societysphere.exception.ResourceNotFoundException;
import com.societysphere.repository.AdminRepository;
import com.societysphere.repository.SocietyRepository;
import com.societysphere.repository.UserRepository;
import com.societysphere.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;

@RestController
@RequestMapping("/api/superadmin/societies")
@RequiredArgsConstructor
public class SocietyApprovalController {
    private final SocietyRepository societies;
    private final UserRepository users;
    private final AdminRepository admins;
    private final PasswordEncoder encoder;
    private final JavaMailSender mail;

    @Value("${app.mail.enabled:false}")
    private boolean mailEnabled;

    @Value("${spring.mail.username:}")
    private String senderEmail;

    @PutMapping("/{id}/status")
    public ApiResponse<String> update(@PathVariable Long id, @RequestParam boolean approve) {
        Society society = societies.findById(id).orElseThrow(() -> new ResourceNotFoundException("Society not found"));
        if (!approve) {
            society.setActive(false);
            society.setRegistrationStatus(RegistrationStatus.REJECTED);
            societies.save(society);
            return ApiResponse.success("Registration rejected", null);
        }
        if (!admins.findBySociety(society).isEmpty()) return ApiResponse.success("Society is already approved", null);
        if (users.findByEmail(society.getEmail()).isPresent()) throw new BadRequestException("An account already uses this email");

        String password = temporaryPassword();
        User user = users.save(User.builder().email(society.getEmail()).password(encoder.encode(password)).role(UserRole.ADMIN).accountStatus(AccountStatus.ACTIVE).emailVerified(true).firstLogin(true).build());
        admins.save(Admin.builder().user(user).society(society).fullName(society.getApplicantAdminName()).mobileNumber(society.getPhoneNumber()).adminType(AdminType.OWNER).build());
        society.setActive(true);
        society.setRegistrationStatus(RegistrationStatus.APPROVED);
        societies.save(society);
        return deliverCredentials(user, password, "Approved, but email delivery is disabled. Set APP_MAIL_ENABLED=true and configure MAIL_USERNAME and MAIL_PASSWORD, then use Manage to resend credentials.");
    }

    @PostMapping("/{id}/send-credentials")
    public ApiResponse<String> resendCredentials(@PathVariable Long id) {
        Society society = societies.findById(id).orElseThrow(() -> new ResourceNotFoundException("Society not found"));
        Admin admin = admins.findBySociety(society).stream().findFirst().orElseThrow(() -> new BadRequestException("Society does not have an administrator account"));
        String password = temporaryPassword();
        admin.getUser().setPassword(encoder.encode(password));
        users.save(admin.getUser());
        return deliverCredentials(admin.getUser(), password, "Email delivery is disabled. Set APP_MAIL_ENABLED=true and configure MAIL_USERNAME and MAIL_PASSWORD first.");
    }

    private String temporaryPassword() {
        return "SS-" + Integer.toHexString(new SecureRandom().nextInt()).toUpperCase();
    }

    private ApiResponse<String> deliverCredentials(User user, String password, String disabledMessage) {
        if (!mailEnabled) return ApiResponse.success(disabledMessage, null);
        if (senderEmail == null || senderEmail.isBlank()) return ApiResponse.success("Email delivery is enabled, but MAIL_USERNAME is missing. Restart the backend after setting it.", null);
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(senderEmail);
            message.setTo(user.getEmail());
            message.setSubject("Society Sphere administrator credentials");
            message.setText("Your Society Sphere administrator account is ready.\n\nLogin email: " + user.getEmail() + "\nTemporary password: " + password + "\n\nPlease sign in and complete your profile.");
            mail.send(message);
            return ApiResponse.success("Credentials emailed to the society administrator", null);
        } catch (MailException ex) {
            return ApiResponse.success("Account approved, but Gmail rejected the credentials email. Verify that MAIL_USERNAME and the Google App Password belong to the same account, then restart and resend from Manage.", null);
        }
    }
}