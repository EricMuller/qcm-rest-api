package com.emu.apps.qcm.rest.controllers.unrestrained;

import com.emu.apps.qcm.rest.controllers.unrestrained.hal.ApiAssembler;
import com.emu.apps.qcm.rest.controllers.unrestrained.resources.ApiResource;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.emu.apps.qcm.rest.controllers.unrestrained.PublicMappings.PUBLIC_API;
import static com.emu.apps.shared.security.AccountContextHolder.getPrincipal;

@RestController
@RequestMapping(PUBLIC_API)
@Tag(name = "_docs", description = "hypermedia and links")
public class RootDocsRestController {

    private final ApiAssembler apiAssembler;

    public RootDocsRestController(ApiAssembler apiAssembler) {

        this.apiAssembler = apiAssembler;
    }


    @GetMapping("/")
    public EntityModel <ApiResource> root() {
        return apiAssembler.toModel(new ApiResource(getPrincipal()));
    }
}
