package com.emu.apps.qcm.infra.webmvc.config;

import com.emu.apps.qcm.domain.models.User;
import com.emu.apps.qcm.domain.repositories.UserRepository;
import com.emu.apps.qcm.infra.webmvc.exceptions.UserAuthenticationException;
import com.emu.apps.shared.security.AuthentificationContextHolder;
import com.emu.apps.shared.security.PrincipalUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

@Component("UserFilter")
@Slf4j
public class UserFilter implements Filter {

    private final UserRepository userServicePort;

    public UserFilter(UserRepository userServicePort) {
        this.userServicePort = userServicePort;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (!HttpMethod.OPTIONS.name().equals(request.getMethod())) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            AuthentificationContextHolder.setPrincipal(null);
            if (Objects.nonNull(authentication)) {
                String principal = PrincipalUtils.getEmailOrName(authentication.getPrincipal());
                if (Objects.nonNull(principal)) {
                    final User user = userServicePort.userByEmail(principal);
                    if (Objects.nonNull(user)) {
                        AuthentificationContextHolder.setPrincipal(user.getUuid());
                        GrantedAuthority grantedAuthority = () -> user.getUuid();
                    } else {
                        LOGGER.warn("user with email {} non enregistré!", principal);
                        throw new UserAuthenticationException(principal);
                    }
                }
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
