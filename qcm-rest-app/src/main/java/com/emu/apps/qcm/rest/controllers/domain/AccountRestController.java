package com.emu.apps.qcm.rest.controllers.domain;

import com.emu.apps.qcm.domain.model.account.Account;
import com.emu.apps.qcm.domain.model.account.AccountRepository;
import com.emu.apps.qcm.rest.controllers.domain.hal.AccountModelAssembler;
import com.emu.apps.qcm.rest.controllers.domain.jsonview.AccountView;
import com.emu.apps.qcm.rest.controllers.domain.resources.AccountResource;
import com.emu.apps.qcm.rest.controllers.domain.mappers.QcmResourceMapper;
import com.emu.apps.shared.exceptions.I18nedBadRequestException;
import com.emu.apps.shared.exceptions.I18nedForbiddenRequestException;
import com.fasterxml.jackson.annotation.JsonView;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

import static com.emu.apps.qcm.rest.controllers.domain.DomainMappings.ACCOUNTS;
import static com.emu.apps.qcm.rest.controllers.domain.DomainMappings.DOMAIN_API;
import static com.emu.apps.shared.exceptions.I18nedMessageSupport.EXISTING_UUID_ACCOUNT;
import static com.emu.apps.shared.exceptions.I18nedMessageSupport.INVALID_EMAIL_USER;
import static com.emu.apps.shared.security.PrincipalUtils.getAttribute;
import static com.emu.apps.shared.security.PrincipalUtils.getEmailOrName;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(DOMAIN_API + ACCOUNTS)
@Tag(name = "Account")
@Timed("accounts")
public class AccountRestController {

    private final AccountRepository accountRepository;

    private final QcmResourceMapper qcmResourceMapper;

    private final AccountModelAssembler accountModelAssembler;

    public AccountRestController(AccountRepository accountRepository, QcmResourceMapper qcmResourceMapper, AccountModelAssembler accountModelAssembler) {
        this.accountRepository = accountRepository;
        this.qcmResourceMapper = qcmResourceMapper;
        this.accountModelAssembler = accountModelAssembler;
    }

    public Map <String, String> principal(Principal principal) {
        return accountRepository.principal(principal);
    }

    /**
     * public String whoami(@AuthenticationPrincipal(expression="name") String name) {
     *
     * @param jwt
     * @return
     */
    @GetMapping("/token")
    @Timed("accounts.token")
    public String token(@Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt) {

        return jwt.getTokenValue();
    }

    /**
     * @param principal : authentified user
     * @return User
     */
    @GetMapping(value = "/me", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Object", content = @Content(schema = @Schema(name = "AccountResource", implementation = AccountResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")})
    @Timed("accounts.getAuthentifiedUser")
    public EntityModel <AccountResource> getAuthentifiedUser(Principal principal) {
        String email = getEmailOrName(principal);
        Account account = accountRepository.getAccountByEmail(email);
        if (isNull(account)) {
            account = new Account();
            account.setEmail(getAttribute(principal, "email"));
            account.setUserName(getAttribute(principal, "preferred_username"));
        }
        return EntityModel.of(qcmResourceMapper.accountToAccountResource(account));
    }

    //    @PostAuthorize("hasAuthority('PROFIL_CREATED')")
    @PutMapping(value = "/me", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Object updated", content = @Content(schema = @Schema(name = "AccountResource", implementation = AccountResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")})
    @Timed("accounts.updateAuthentifiedUser")
    public EntityModel <AccountResource> updateAuthentifiedUser(@JsonView(AccountView.UpdateAccount.class) @RequestBody AccountResource accountResource, Principal principal) {

        var user = qcmResourceMapper.accountResourceToModel(accountResource);
        String email = getEmailOrName(principal);
        Account authentAccount = accountRepository.getAccountByEmail(email);
        if (nonNull(authentAccount) && authentAccount.getEmail().equals(user.getEmail())) {
            return EntityModel.of(qcmResourceMapper.accountToAccountResource(accountRepository.updateAccount(user)));
        } else {
            throw new I18nedForbiddenRequestException(INVALID_EMAIL_USER);
        }
    }

    @PostMapping(value = "/me", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Object created", content = @Content(schema = @Schema(name = "AccountResource", implementation = AccountResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")})
    @Timed("accounts.createAuthentifiedUser")
    public ResponseEntity <EntityModel <AccountResource>> createAuthentifiedUser(@JsonView(AccountView.CreateAccount.class) @RequestBody AccountResource accountResource, Principal principal) {
        var user = qcmResourceMapper.accountResourceToModel(accountResource);
        String email = getEmailOrName(principal);
        Account authentAccount = accountRepository.getAccountByEmail(email);
        if (isNull(authentAccount)) {
            user.setEmail(email);
            var entityModel = EntityModel.of(qcmResourceMapper.accountToAccountResource(accountRepository.createAccount(user)));
            this.accountModelAssembler.addLinks(entityModel);
            return ResponseEntity
                    .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                    .body(entityModel);

        } else {
            throw new I18nedBadRequestException(EXISTING_UUID_ACCOUNT, email);
        }
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Object updated", content = @Content(schema = @Schema(name = "AccountResource", implementation = AccountResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")})
    @Timed("accounts.updateUser")
    public EntityModel <AccountResource> updateUser(@RequestBody AccountResource accountResource, Principal principal) {
        var account = qcmResourceMapper.accountResourceToModel(accountResource);
        // fixme: ???
        return EntityModel.of(qcmResourceMapper.accountToAccountResource(accountRepository.updateAccount(account)));
    }

    @GetMapping(value = "/{uuid}", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Object", content = @Content(schema = @Schema(name = "AccountResource", implementation = AccountResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")})
    @Timed("accounts.getUserById")
    public EntityModel <AccountResource> getUserById(@PathVariable("uuid") String uuid ) {

        var account = accountRepository.getAccountById(uuid);

        return EntityModel.of(qcmResourceMapper.accountToAccountResource(account));
    }

}
