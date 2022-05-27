package com.emu.apps.qcm.rest.controllers.unrestrained;

import com.emu.apps.qcm.domain.model.account.Account;
import com.emu.apps.qcm.domain.model.account.AccountRepository;
import com.emu.apps.qcm.rest.controllers.management.resources.AccountResource;
import com.emu.apps.qcm.rest.mappers.QcmResourceMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.ACCOUNTS;
import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.PUBLIC_API;
import static com.emu.apps.shared.security.PrincipalUtils.getEmailOrName;
import static java.util.Objects.isNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(PUBLIC_API + ACCOUNTS)
@Tag(name = "Account")
public class AccountRestPublicController {

    private final AccountRepository accountRepository;

    private final QcmResourceMapper qcmResourceMapper;

    public AccountRestPublicController(AccountRepository userServicePort, QcmResourceMapper qcmResourceMapper) {
        this.accountRepository = userServicePort;
        this.qcmResourceMapper = qcmResourceMapper;
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
            account.setEmail(getEmailOrName(principal));
        }
        return EntityModel.of(qcmResourceMapper.accountToAccountResource(account));
    }

}
