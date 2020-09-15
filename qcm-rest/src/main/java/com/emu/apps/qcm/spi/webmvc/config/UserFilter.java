package com.emu.apps.qcm.spi.webmvc.config;

import com.emu.apps.qcm.api.models.User;
import com.emu.apps.qcm.domain.ports.UserServicePort;
import com.emu.apps.shared.security.AuthentificationContextHolder;
import com.emu.apps.shared.security.PrincipalUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.Objects;

@Component
@Slf4j
public class UserFilter implements Filter {

    private final UserServicePort userServicePort;

    public UserFilter(UserServicePort userServicePort) {
        this.userServicePort = userServicePort;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthentificationContextHolder.setUser(null);
        if (Objects.nonNull(authentication)) {
            String principal = PrincipalUtils.getEmailOrName(authentication.getPrincipal());
            if (Objects.nonNull(principal)) {
                final User user = userServicePort.userByEmail(principal);
                if (Objects.nonNull(user)) {
                    AuthentificationContextHolder.setUser(user.getUuid());
                    GrantedAuthority grantedAuthority = () -> user.getUuid();
                }
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
