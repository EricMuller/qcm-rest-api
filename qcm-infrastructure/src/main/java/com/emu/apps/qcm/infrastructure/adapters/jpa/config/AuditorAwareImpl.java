package com.emu.apps.qcm.infrastructure.adapters.jpa.config;

import com.emu.apps.qcm.infrastructure.exceptions.TechnicalException;
import com.emu.apps.shared.security.UserContextHolder;
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
            String user = UserContextHolder.getUser();
            return Objects.nonNull(user) ? Optional.of(UserContextHolder.getUser()) : Optional.empty();

//            if (Objects.isNull(user)) {
//                user = userRepository.findByEmailContaining(PrincipalUtils.getEmail(authentication)).orElse(null);
//                userLocal.set(user);
//            }
//            return Objects.isNull(user) ? Optional.empty() : Optional.of(user.getUuid().toString());

            //return Optional.of(PrincipalUtils.getEmail(authentication));
        }
    }
}
