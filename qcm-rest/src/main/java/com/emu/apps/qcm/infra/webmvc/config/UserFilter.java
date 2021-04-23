package com.emu.apps.qcm.infra.webmvc.config;

import com.emu.apps.qcm.domain.model.user.User;

import com.emu.apps.qcm.domain.model.user.UserRepository;
import com.emu.apps.shared.security.PrincipalUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.emu.apps.qcm.infra.webmvc.rest.ApiRestMappings.PUBLIC_API;
import static com.emu.apps.qcm.infra.webmvc.rest.ApiRestMappings.USERS;
import static com.emu.apps.shared.security.AuthentificationContextHolder.setPrincipal;
import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;

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
            setPrincipal(null);
            if (Objects.nonNull(authentication)) {
                String principal = PrincipalUtils.getEmailOrName(authentication.getPrincipal());
                if (Objects.nonNull(principal)) {
                    final User user = userServicePort.userByEmail(principal);
                    if (Objects.nonNull(user)) {
                        setPrincipal(user.getUuid());
                        GrantedAuthority grantedAuthority = () -> user.getUuid();
                    } else {
                        LOGGER.warn("user with email {} non enregistré!", principal);
                        if (!request.getRequestURI().startsWith(PUBLIC_API + USERS)) {
                            LOGGER.warn("user with email {} non enregistré! send http 303 ", principal);
                            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
                            httpResponse.sendError(SC_FORBIDDEN, "Required valid email in database");
                        } else {
                            LOGGER.warn("user with email {} non enregistré!", principal);
                        }
                    }
                }
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
