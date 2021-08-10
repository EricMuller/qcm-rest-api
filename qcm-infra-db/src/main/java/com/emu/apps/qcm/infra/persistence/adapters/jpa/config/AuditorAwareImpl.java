package com.emu.apps.qcm.infra.persistence.adapters.jpa.config;


import com.emu.apps.shared.exceptions.TechnicalException;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.emu.apps.shared.security.AuthentificationContextHolder.getPrincipal;
import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Service(value = "auditorAware")
@Profile("!test")
public class AuditorAwareImpl implements AuditorAware <String> {

    @Override
    @Transactional(readOnly = true)
    public Optional <String> getCurrentAuditor() {
        var authentication = getContext().getAuthentication();
        if (isNull(authentication) || !authentication.isAuthenticated()) {
            throw new TechnicalException("authent");
        } else {
            return ofNullable(getPrincipal().getName());
        }
    }
}
