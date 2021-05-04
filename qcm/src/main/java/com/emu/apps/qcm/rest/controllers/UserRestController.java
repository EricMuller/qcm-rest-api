package com.emu.apps.qcm.rest.controllers;

import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.user.User;
import com.emu.apps.qcm.domain.model.user.UserRepository;
import com.emu.apps.qcm.rest.controllers.mappers.QuestionnaireResourcesMapper;
import com.emu.apps.qcm.rest.controllers.resources.UserResources;
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
import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.USERS;
import static com.emu.apps.shared.exceptions.MessageSupport.EXISTS_UUID_USER;
import static com.emu.apps.shared.exceptions.MessageSupport.INVALID_UUID_USER;
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

    private final QuestionnaireResourcesMapper questionnaireResourcesMapper;


    public UserRestController(UserRepository userServicePort, QuestionnaireResourcesMapper questionnaireResourcesMapper) {
        this.userRepository = userServicePort;
        this.questionnaireResourcesMapper = questionnaireResourcesMapper;
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
    public UserResources getAuthentifiedUser(Principal principal) {
        String email = getEmailOrName(principal);
        User user = userRepository.userByEmail(email);
        if (isNull(user)) {
            user = new User();
            user.setEmail(getEmailOrName(principal));
        }
        return questionnaireResourcesMapper.userToResources(user);
    }

    //    @PostAuthorize("hasAuthority('PROFIL_CREATED')")
    @PutMapping(value = "/me", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserResources updateAuthentifiedUser(@RequestBody UserResources userResources, Principal principal) {

        var user = questionnaireResourcesMapper.userToModel(userResources);
        String email = getEmailOrName(principal);
        User authentUser = userRepository.userByEmail(email);
        if (nonNull(authentUser) && authentUser.getEmail().equals(user.getEmail())) {
            return questionnaireResourcesMapper.userToResources(userRepository.updateUser(user, new PrincipalId(email)));
        } else {
            throw new UserAuthenticationException(INVALID_UUID_USER);
        }
    }

    @PostMapping(value = "/me", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserResources createAuthentifiedUser(@RequestBody UserResources userResources, Principal principal) {
        var user = questionnaireResourcesMapper.userToModel(userResources);
        String email = getEmailOrName(principal);
        User authentUser = userRepository.userByEmail(email);
        if (isNull(authentUser)) {
            return questionnaireResourcesMapper.userToResources(userRepository.createUser(user, new PrincipalId(email)));
        } else {
            throw new UserAuthenticationException(EXISTS_UUID_USER);
        }
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public UserResources updateUser(@RequestBody UserResources userResources, Principal principal) {
        var user = questionnaireResourcesMapper.userToModel(userResources);
        return questionnaireResourcesMapper.userToResources(userRepository.updateUser(user, new PrincipalId(getEmailOrName(principal))));
    }

}
