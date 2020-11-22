package com.emu.apps.qcm.spi.webmvc.rest;

import com.emu.apps.qcm.aggregates.User;
import com.emu.apps.qcm.repositories.UserRepository;
import com.emu.apps.qcm.spi.persistence.exceptions.MessageSupport;
import com.emu.apps.qcm.spi.webmvc.exceptions.UserAuthenticationException;
import com.emu.apps.shared.security.PrincipalUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;
import java.util.Objects;

import static com.emu.apps.qcm.spi.webmvc.rest.ApiRestMappings.PUBLIC_API;
import static com.emu.apps.qcm.spi.webmvc.rest.ApiRestMappings.USERS;

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
    @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public User getAuthentifiedUser(Principal principal) {
        String email = PrincipalUtils.getEmailOrName(principal);
        User user = userRepository.userByEmail(email);
        if (Objects.isNull(user)) {
            user = new User();
            user.setEmail(PrincipalUtils.getEmailOrName(principal));
        }
        return user;
    }

//    @PostAuthorize("hasAuthority('PROFIL_CREATED')")
    @PutMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public User updateAuthentifiedUser(@RequestBody User user, Principal principal) {
        String email = PrincipalUtils.getEmailOrName(principal);
        User authentUser = userRepository.userByEmail(email);
        if (Objects.nonNull(authentUser) && authentUser.getEmail().equals(user.getEmail())) {
            return userRepository.updateUser(user, email);
        } else {
            throw new UserAuthenticationException(MessageSupport.INVALID_UUID_USER);
        }
    }

    @PostMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public User createAuthentifiedUser(@RequestBody User user, Principal principal) {
        String email = PrincipalUtils.getEmailOrName(principal);
        User authentUser = userRepository.userByEmail(email);
        if (Objects.isNull(authentUser)) {
            return userRepository.createUser(user, email);
        } else {
            throw new UserAuthenticationException(MessageSupport.EXISTS_UUID_USER);
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public User updateUser(@RequestBody User user, Principal principal) {
        return userRepository.updateUser(user, PrincipalUtils.getEmailOrName(principal));
    }

}
