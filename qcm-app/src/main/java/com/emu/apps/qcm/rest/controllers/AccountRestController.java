package com.emu.apps.qcm.rest.controllers;

import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.user.Account;
import com.emu.apps.qcm.domain.model.user.AccountRepository;
import com.emu.apps.qcm.rest.controllers.mappers.QuestionnaireResourcesMapper;
import com.emu.apps.qcm.rest.controllers.resources.AccountResources;
import com.emu.apps.qcm.rest.exceptions.UserAuthenticationException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;
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

    private final QuestionnaireResourcesMapper questionnaireResourcesMapper;


    public AccountRestController(AccountRepository userServicePort, QuestionnaireResourcesMapper questionnaireResourcesMapper) {
        this.accountRepository = userServicePort;
        this.questionnaireResourcesMapper = questionnaireResourcesMapper;
    }

    public Map <String, String> principal(Principal principal) {
        return accountRepository.principal(principal);
    }

    /**
     * @param principal : authentified user
     * @return User
     */
    @GetMapping(value = "/me", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public AccountResources getAuthentifiedUser(Principal principal) {
        String email = getEmailOrName(principal);
        Account account = accountRepository.userByEmail(email);
        if (isNull(account)) {
            account = new Account();
            account.setEmail(getEmailOrName(principal));
        }
        return questionnaireResourcesMapper.userToResources(account);
    }

    //    @PostAuthorize("hasAuthority('PROFIL_CREATED')")
    @PutMapping(value = "/me", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public AccountResources updateAuthentifiedUser(@RequestBody AccountResources accountResources, Principal principal) {

        var user = questionnaireResourcesMapper.userToModel(accountResources);
        String email = getEmailOrName(principal);
        Account authentAccount = accountRepository.userByEmail(email);
        if (nonNull(authentAccount) && authentAccount.getEmail().equals(user.getEmail())) {
            return questionnaireResourcesMapper.userToResources(accountRepository.updateUser(user, new PrincipalId(email)));
        } else {
            throw new UserAuthenticationException(INVALID_UUID_USER);
        }
    }

    @PostMapping(value = "/me", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public AccountResources createAuthentifiedUser(@RequestBody AccountResources accountResources, Principal principal) {
        var user = questionnaireResourcesMapper.userToModel(accountResources);
        String email = getEmailOrName(principal);
        Account authentAccount = accountRepository.userByEmail(email);
        if (isNull(authentAccount)) {
            return questionnaireResourcesMapper.userToResources(accountRepository.createUser(user, new PrincipalId(email)));
        } else {
            throw new UserAuthenticationException(EXISTS_UUID_USER);
        }
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public AccountResources updateUser(@RequestBody AccountResources accountResources, Principal principal) {
        var user = questionnaireResourcesMapper.userToModel(accountResources);
        return questionnaireResourcesMapper.userToResources(accountRepository.updateUser(user, new PrincipalId(getEmailOrName(principal))));
    }

}
