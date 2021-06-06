package com.emu.apps.qcm.rest.controllers;

import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.user.Account;
import com.emu.apps.qcm.domain.model.user.AccountRepository;
import com.emu.apps.qcm.rest.mappers.QuestionnaireResourceMapper;
import com.emu.apps.qcm.rest.resources.AccountResource;
import com.emu.apps.qcm.rest.resources.openui.AccountView;
import com.emu.apps.qcm.rest.exceptions.UserAuthenticationException;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.PUBLIC_API;
import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.ACCOUNTS;
import static com.emu.apps.shared.exceptions.MessageSupport.EXISTS_UUID_USER;
import static com.emu.apps.shared.exceptions.MessageSupport.INVALID_UUID_USER;
import static com.emu.apps.shared.security.PrincipalUtils.getEmailOrName;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Profile("webmvc")
@RequestMapping(PUBLIC_API + ACCOUNTS)
@Tag(name = "Account")
public class AccountRestController {

    private final AccountRepository accountRepository;

    private final QuestionnaireResourceMapper questionnaireResourceMapper;

    public AccountRestController(AccountRepository userServicePort, QuestionnaireResourceMapper questionnaireResourceMapper) {
        this.accountRepository = userServicePort;
        this.questionnaireResourceMapper = questionnaireResourceMapper;
    }

    public Map <String, String> principal(Principal principal) {
        return accountRepository.principal(principal);
    }

    @GetMapping("/whoami")
    public String whoami(@AuthenticationPrincipal Jwt jwt) {
        //public String whoami(@AuthenticationPrincipal(expression="name") String name) {
        return jwt.getTokenValue();
    }

    /**
     * @param principal : authentified user
     * @return User
     */
    @GetMapping(value = "/me", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public AccountResource getAuthentifiedUser(Principal principal) {
        String email = getEmailOrName(principal);
        Account account = accountRepository.userByEmail(email);
        if (isNull(account)) {
            account = new Account();
            account.setEmail(getEmailOrName(principal));
        }
        return questionnaireResourceMapper.userToResources(account);
    }

    //    @PostAuthorize("hasAuthority('PROFIL_CREATED')")
    @PutMapping(value = "/me", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public AccountResource updateAuthentifiedUser(@JsonView(AccountView.Update.class) @RequestBody AccountResource accountResource, Principal principal) {

        var user = questionnaireResourceMapper.userToModel(accountResource);
        String email = getEmailOrName(principal);
        Account authentAccount = accountRepository.userByEmail(email);
        if (nonNull(authentAccount) && authentAccount.getEmail().equals(user.getEmail())) {
            return questionnaireResourceMapper.userToResources(accountRepository.updateUser(user, new PrincipalId(email)));
        } else {
            throw new UserAuthenticationException(INVALID_UUID_USER);
        }
    }

    @PostMapping(value = "/me", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public AccountResource createAuthentifiedUser(@JsonView(AccountView.Create.class) @RequestBody AccountResource accountResource, Principal principal) {
        var user = questionnaireResourceMapper.userToModel(accountResource);
        String email = getEmailOrName(principal);
        Account authentAccount = accountRepository.userByEmail(email);
        if (isNull(authentAccount)) {
            return questionnaireResourceMapper.userToResources(accountRepository.createUser(user, new PrincipalId(email)));
        } else {
            throw new UserAuthenticationException(EXISTS_UUID_USER);
        }
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public AccountResource updateUser(@RequestBody AccountResource accountResource, Principal principal) {
        var user = questionnaireResourceMapper.userToModel(accountResource);
        return questionnaireResourceMapper.userToResources(accountRepository.updateUser(user, new PrincipalId(getEmailOrName(principal))));
    }

}
