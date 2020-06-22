package com.emu.apps.qcm.webmvc.config;

import com.emu.apps.qcm.models.UserDto;
import com.emu.apps.qcm.domain.ports.UserServicePort;
import com.emu.apps.shared.security.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
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
        if (Objects.nonNull(authentication)) {
            UserDto userDto = userServicePort.user(authentication);
            UserContextHolder.setUser(userDto.getUuid());
            // LOGGER.info("{}: {}", PrincipalUtils.getEmail(authentication), userDto.getUuid());
        } else {
            UserContextHolder.setUser(null);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
