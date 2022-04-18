package com.emu.apps.qcm.rest.controllers.publicized;

import com.emu.apps.qcm.rest.controllers.publicized.hal.ApiAssembler;
import com.emu.apps.qcm.rest.controllers.publicized.resources.ApiResource;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.PUBLICIZED_API;
import static com.emu.apps.shared.security.AuthentificationContextHolder.getPrincipal;

@RestController
@RequestMapping(PUBLICIZED_API)
@Tag(name = "Public")
public class ApiDocsRestController {

    private final ApiAssembler apiAssembler;

    public ApiDocsRestController(ApiAssembler apiAssembler) {

        this.apiAssembler = apiAssembler;
    }


    @GetMapping("/")
    public EntityModel <ApiResource> root() {
        return apiAssembler.toModel(new ApiResource(getPrincipal()));
    }
}
