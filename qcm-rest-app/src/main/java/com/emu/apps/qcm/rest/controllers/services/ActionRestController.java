package com.emu.apps.qcm.rest.controllers.services;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.emu.apps.qcm.rest.controllers.services.ServicesMappings.COMMAND;
import static com.emu.apps.qcm.rest.controllers.services.ServicesMappings.SERVICES_API;


@RestController
@RequestMapping(value = SERVICES_API + COMMAND, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Command")
public class ActionRestController {

    @ResponseBody
    @GetMapping()
    public void command(@PathVariable("uuid") String uuid) throws IOException {


    }


}
