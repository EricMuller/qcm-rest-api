package com.emu.apps.qcm.spi.persistence.adapters.jpa.config;

import com.emu.apps.qcm.spi.persistence.exceptions.TechnicalException;
import com.emu.apps.shared.security.AuthentificationContextHolder;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service(value = "auditorAware")
public class AuditorAwareImpl implements AuditorAware <String> {

    @Override
    @Transactional(readOnly = true)
    public Optional <String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new TechnicalException("authent");
        } else {
            String user = AuthentificationContextHolder.getUser();
            return Objects.nonNull(user) ? Optional.of(AuthentificationContextHolder.getUser()) : Optional.empty();
        }
    }
}
