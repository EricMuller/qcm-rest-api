package com.emu.apps.qcm.rest.config;

import com.emu.apps.qcm.application.AccountService;
import com.emu.apps.qcm.domain.model.user.Account;
import com.emu.apps.shared.security.PrincipalUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.ACCOUNTS;
import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.PUBLIC_API;
import static com.emu.apps.shared.security.AuthentificationContextHolder.setPrincipal;
import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;

@Component("AccountFilter")
@Slf4j
public class AccountFilter implements Filter {

    private final AccountService accountService;

    public AccountFilter(AccountService accountService) {
        this.accountService = accountService;
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
                    final Account account = accountService.userByEmail(principal);
                    if (Objects.nonNull(account)) {
                        setPrincipal(account.getId().toUuid());
                        // GrantedAuthority grantedAuthority = () -> account.getId().toUuid();
                    } else {
                        LOGGER.warn("user with email {} non enregistré!", principal);
                        if (!request.getRequestURI().startsWith(PUBLIC_API + ACCOUNTS)) {
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
