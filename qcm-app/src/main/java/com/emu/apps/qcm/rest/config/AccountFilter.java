package com.emu.apps.qcm.rest.config;

import com.emu.apps.qcm.application.AccountService;
import com.emu.apps.qcm.domain.model.account.Account;
import com.emu.apps.qcm.domain.model.account.AccountId;
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
import java.util.UUID;

import static com.emu.apps.qcm.domain.model.account.AccountId.of;
import static com.emu.apps.shared.security.AuthentificationContextHolder.setPrincipal;
import static com.emu.apps.shared.security.PrincipalUtils.USER_NAME_ATTRIBUTE;
import static com.emu.apps.shared.security.PrincipalUtils.getAttribute;
import static java.util.Objects.nonNull;
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
            if (nonNull(authentication)) {
                String principal = PrincipalUtils.getEmailOrName(authentication.getPrincipal());
                if (nonNull(principal)) {
                    Account account = accountService.getAccountByEmail(principal);
                    if (nonNull(account)) {
                        setPrincipal(account);
                    } else {
                        LOGGER.warn("No account with email {} !", principal);
                        String username = getAttribute(authentication, USER_NAME_ATTRIBUTE);
                        AccountId accountId =  of(UUID.randomUUID());
                        setPrincipal(accountId::toUuid);
                        account = accountService.createNewAccount(principal, username, accountId);
                        if (nonNull(account)) {
                            setPrincipal(account);
                        } else {
                            LOGGER.warn("No account with email {} ! send http 303 ", principal);
                            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
                            httpResponse.sendError(SC_FORBIDDEN, "Required valid email in account database");
                        }
                    }
                }
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
