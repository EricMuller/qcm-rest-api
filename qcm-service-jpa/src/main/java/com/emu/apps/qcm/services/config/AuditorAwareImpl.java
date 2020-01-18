package com.emu.apps.qcm.services.config;

import com.emu.apps.shared.security.PrincipalUtils;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware <String> {
    @Override
    public Optional <String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }
        return Optional.of(PrincipalUtils.getEmail(authentication));
    }

}
