package com.emu.apps.qcm.services.jpa.config;

import org.keycloak.KeycloakPrincipal;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public String getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        // Can use Spring Security to return currently logged in user
        return ((KeycloakPrincipal) authentication.getPrincipal()).getName();
    }
}