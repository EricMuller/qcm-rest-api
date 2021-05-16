package com.emu.apps.qcm.infra.persistence.adapters.jpa.config;


import com.emu.apps.shared.exceptions.TechnicalException;
import com.emu.apps.shared.security.AuthentificationContextHolder;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service(value = "auditorAware")
@Profile("!test")
public class AuditorAwareImpl implements AuditorAware <String> {

    @Override
    @Transactional(readOnly = true)
    public Optional <String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new TechnicalException("authent");
        } else {
            String principal = AuthentificationContextHolder.getPrincipal();
            return Objects.nonNull(principal) ? Optional.of(principal) : Optional.empty();
        }
    }
}
