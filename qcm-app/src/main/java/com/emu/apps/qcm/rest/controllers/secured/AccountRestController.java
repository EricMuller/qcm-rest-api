package com.emu.apps.qcm.rest.controllers.secured;

import com.emu.apps.qcm.domain.model.account.Account;
import com.emu.apps.qcm.domain.model.account.AccountRepository;
import com.emu.apps.qcm.rest.controllers.secured.openui.AccountView;
import com.emu.apps.qcm.rest.controllers.secured.resources.AccountResource;
import com.emu.apps.shared.exceptions.I18nedBadRequestException;
import com.emu.apps.qcm.rest.mappers.QuestionnaireResourceMapper;
import com.emu.apps.shared.exceptions.I18nedForbiddenRequestException;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Profile;
import org.springframework.hateoas.EntityModel;
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

import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.ACCOUNTS;
import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.PROTECTED_API;
import static com.emu.apps.shared.exceptions.I18nedMessageSupport.EXISTING_UUID_ACCOUNT;
import static com.emu.apps.shared.exceptions.I18nedMessageSupport.INVALID_EMAIL_USER;
import static com.emu.apps.shared.security.PrincipalUtils.getAttribute;
import static com.emu.apps.shared.security.PrincipalUtils.getEmailOrName;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Profile("webmvc")
@RequestMapping(PROTECTED_API + ACCOUNTS)
@Tag(name = "Account")
public class AccountRestController {

    private final AccountRepository accountRepository;

    private final QuestionnaireResourceMapper questionnaireResourceMapper;

    public AccountRestController(AccountRepository accountRepository, QuestionnaireResourceMapper questionnaireResourceMapper) {
        this.accountRepository = accountRepository;
        this.questionnaireResourceMapper = questionnaireResourceMapper;
    }

    public Map <String, String> principal(Principal principal) {
        return accountRepository.principal(principal);
    }

    /**
     * public String whoami(@AuthenticationPrincipal(expression="name") String name) {
     * @param jwt
     * @return
     */
    @GetMapping("/token")
    public String token(@Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt) {

        return jwt.getTokenValue();
    }

    /**
     * @param principal : authentified user
     * @return User
     */
    @GetMapping(value = "/me", produces = APPLICATION_JSON_VALUE)
    @ResponseBody

    public EntityModel <AccountResource> getAuthentifiedUser(Principal principal) {
        String email = getEmailOrName(principal);
        Account account = accountRepository.getAccountByEmail(email);
        if (isNull(account)) {
            account = new Account();
            account.setEmail(getAttribute(principal, "email"));
            account.setUserName(getAttribute(principal, "preferred_username"));
        }
        return EntityModel.of(questionnaireResourceMapper.accountToResources(account));
    }

    //    @PostAuthorize("hasAuthority('PROFIL_CREATED')")
    @PutMapping(value = "/me", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public EntityModel <AccountResource> updateAuthentifiedUser(@JsonView(AccountView.Update.class) @RequestBody AccountResource accountResource, Principal principal) {

        var user = questionnaireResourceMapper.accountToModel(accountResource);
        String email = getEmailOrName(principal);
        Account authentAccount = accountRepository.getAccountByEmail(email);
        if (nonNull(authentAccount) && authentAccount.getEmail().equals(user.getEmail())) {
            return EntityModel.of(questionnaireResourceMapper.accountToResources(accountRepository.updateAccount(user)));
        } else {
            throw new I18nedForbiddenRequestException(INVALID_EMAIL_USER);
        }
    }

    @PostMapping(value = "/me", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public EntityModel <AccountResource> createAuthentifiedUser(@JsonView(AccountView.Create.class) @RequestBody AccountResource accountResource, Principal principal) {
        var user = questionnaireResourceMapper.accountToModel(accountResource);
        String email = getEmailOrName(principal);
        Account authentAccount = accountRepository.getAccountByEmail(email);
        if (isNull(authentAccount)) {
            user.setEmail(email);
            return EntityModel.of(questionnaireResourceMapper.accountToResources(accountRepository.createAccount(user)));
        } else {
            throw new I18nedBadRequestException(EXISTING_UUID_ACCOUNT, email);
        }
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public EntityModel <AccountResource> updateUser(@RequestBody AccountResource accountResource, Principal principal) {
        var account = questionnaireResourceMapper.accountToModel(accountResource);
        // fixme: ???
        return EntityModel.of(questionnaireResourceMapper.accountToResources(accountRepository.updateAccount(account)));
    }

}
