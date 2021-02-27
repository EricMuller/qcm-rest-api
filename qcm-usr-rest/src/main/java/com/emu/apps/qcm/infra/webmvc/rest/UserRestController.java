package com.emu.apps.qcm.infra.webmvc.rest;

import com.emu.apps.qcm.domain.models.User;
import com.emu.apps.qcm.domain.repositories.UserRepository;
import com.emu.apps.qcm.infra.webmvc.exceptions.UserAuthenticationException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;
import java.util.Objects;

import static com.emu.apps.qcm.infra.persistence.exceptions.MessageSupport.EXISTS_UUID_USER;
import static com.emu.apps.qcm.infra.persistence.exceptions.MessageSupport.INVALID_UUID_USER;
import static com.emu.apps.qcm.infra.webmvc.rest.ApiRestMappings.PUBLIC_API;
import static com.emu.apps.qcm.infra.webmvc.rest.ApiRestMappings.USERS;
import static com.emu.apps.shared.security.PrincipalUtils.getEmailOrName;
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
        if (Objects.isNull(user)) {
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
        if (Objects.nonNull(authentUser) && authentUser.getEmail().equals(user.getEmail())) {
            return userRepository.updateUser(user, email);
        } else {
            throw new UserAuthenticationException(INVALID_UUID_USER);
        }
    }

    @PostMapping(value = "/me", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public User createAuthentifiedUser(@RequestBody User user, Principal principal) {
        String email = getEmailOrName(principal);
        User authentUser = userRepository.userByEmail(email);
        if (Objects.isNull(authentUser)) {
            return userRepository.createUser(user, email);
        } else {
            throw new UserAuthenticationException(EXISTS_UUID_USER);
        }
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public User updateUser(@RequestBody User user, Principal principal) {
        return userRepository.updateUser(user, getEmailOrName(principal));
    }

}
