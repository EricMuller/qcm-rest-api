package com.emu.apps.qcm.rest.controllers.publicized;

import com.emu.apps.qcm.domain.model.user.Account;
import com.emu.apps.qcm.domain.model.user.AccountRepository;
import com.emu.apps.qcm.rest.controllers.secured.resources.AccountResource;
import com.emu.apps.qcm.rest.mappers.QuestionnaireResourceMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Profile;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.ACCOUNTS;
import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.PUBLICIZED_API;
import static com.emu.apps.shared.security.PrincipalUtils.getEmailOrName;
import static java.util.Objects.isNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Profile("webmvc")
@RequestMapping(PUBLICIZED_API + ACCOUNTS)
@Tag(name = "Account")
public class AccountRestPublicController {

    private final AccountRepository accountRepository;

    private final QuestionnaireResourceMapper questionnaireResourceMapper;

    public AccountRestPublicController(AccountRepository userServicePort, QuestionnaireResourceMapper questionnaireResourceMapper) {
        this.accountRepository = userServicePort;
        this.questionnaireResourceMapper = questionnaireResourceMapper;
    }

    /**
     * @param principal : authentified user
     * @return User
     */
    @GetMapping(value = "/me", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public EntityModel <AccountResource> getAuthentifiedUser(Principal principal) {
        String email = getEmailOrName(principal);
        Account account = accountRepository.userByEmail(email);
        if (isNull(account)) {
            account = new Account();
            account.setEmail(getEmailOrName(principal));
        }
        return EntityModel.of(questionnaireResourceMapper.userToResources(account));
    }

}
