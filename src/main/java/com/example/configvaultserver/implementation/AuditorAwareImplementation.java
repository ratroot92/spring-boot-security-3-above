package com.example.configvaultserver.implementation;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.configvaultserver.models.User;

public class AuditorAwareImplementation implements AuditorAware<UUID> {

    @Override
    public Optional<UUID> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof User) {
            User userPrincipal = (User) principal;
            return Optional.of(userPrincipal.getId());
        } else {
            return Optional.empty();
        }

    }
}
