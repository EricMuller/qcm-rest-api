package com.emu.apps.qcm.rest.controllers.unrestrained;

import com.emu.apps.qcm.rest.controllers.unrestrained.resources.LogResource;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.emu.apps.qcm.rest.controllers.unrestrained.PublicMappings.LOGS_API;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(LOGS_API)
@Tag(name = "logs", description = "ngx Log feeder")
@Slf4j
public class NgxLoggerRestController {

    public NgxLoggerRestController() {
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Object created", content = @Content(schema = @Schema(name = "LogResource", implementation = LogResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")})
    @Async
    public void log(@RequestBody @Valid LogResource resource, HttpServletRequest request) {

        resource.setRemoteAddr(request.getRemoteAddr());
        resource.setRemoteHost(request.getRemoteHost());

        switch (resource.getLevel()) {
            case 4:
                LOGGER.warn(resource.toString());
                break;
            case 5:
                LOGGER.error(resource.toString());
                break;
            default:
                LOGGER.info(resource.toString());
        }

    }


}
