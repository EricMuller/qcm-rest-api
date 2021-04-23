package com.emu.apps.qcm.rest.controllers;

import com.emu.apps.qcm.domain.model.user.User;
import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.user.UserRepository;
import com.emu.apps.qcm.rest.exceptions.UserAuthenticationException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

import static com.emu.apps.shared.exceptions.MessageSupport.EXISTS_UUID_USER;
import static com.emu.apps.shared.exceptions.MessageSupport.INVALID_UUID_USER;
import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.PUBLIC_API;
import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.USERS;
import static com.emu.apps.shared.security.PrincipalUtils.getEmailOrName;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Profile("webmvc")
@RequestMapping(PUBLIC_API + USERS)
@Tag(name = "User")
public class UserRestController {

    private final UserRepository userRepository;

    public UserRestController(UserRepository userServicePort) {
        this.userRepository = userServicePort;
    }

    public Map <String, String> principal(Principal principal) {
        return userRepository.principal(principal);
    }

    /**
     * @param principal : authentified user
     * @return User
     */
    @GetMapping(value = "/me", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public User getAuthentifiedUser(Principal principal) {
        String email = getEmailOrName(principal);
        User user = userRepository.userByEmail(email);
        if (isNull(user)) {
            user = new User();
            user.setEmail(getEmailOrName(principal));
        }
        return user;
    }

//    @PostAuthorize("hasAuthority('PROFIL_CREATED')")
    @PutMapping(value = "/me", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public User updateAuthentifiedUser(@RequestBody User user, Principal principal) {
        String email = getEmailOrName(principal);
        User authentUser = userRepository.userByEmail(email);
        if (nonNull(authentUser) && authentUser.getEmail().equals(user.getEmail())) {
            return userRepository.updateUser(user, new PrincipalId(email));
        } else {
            throw new UserAuthenticationException(INVALID_UUID_USER);
        }
    }

    @PostMapping(value = "/me", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public User createAuthentifiedUser(@RequestBody User user, Principal principal) {
        String email = getEmailOrName(principal);
        User authentUser = userRepository.userByEmail(email);
        if (isNull(authentUser)) {
            return userRepository.createUser(user, new PrincipalId(email));
        } else {
            throw new UserAuthenticationException(EXISTS_UUID_USER);
        }
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public User updateUser(@RequestBody User user, Principal principal) {
        return userRepository.updateUser(user, new PrincipalId(getEmailOrName(principal)));
    }

}
