package com.emu.apps.users.web.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.Map;

@CrossOrigin
@Api(value = "user-store",  tags = "Users")
@RequestMapping(UserApi.API_V1 +"/users/me")
@SwaggerDefinition(tags = {
        @Tag(name = "Users", description = "All operations ")
})
public interface UserRestApi {

    @ApiOperation(value = "get Current user", response = Map.class, nickname = "getCurrentUser")
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    Map <String, String> user(Principal principal);
}
