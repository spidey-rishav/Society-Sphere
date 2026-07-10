package com.societysphere.auditing;

import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditorAware")
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        
        /*
         * Later, when Spring Security + JWT is implemented,
         * we'll return the logged-in user's email.
         */

        return Optional.of("SYSTEM");
    }
}