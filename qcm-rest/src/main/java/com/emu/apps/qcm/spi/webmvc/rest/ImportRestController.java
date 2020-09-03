package com.emu.apps.qcm.spi.webmvc.rest;

import com.emu.apps.qcm.api.models.Upload;
import com.emu.apps.qcm.domain.ports.ImportServicePort;
import com.emu.apps.shared.security.AuthentificationContextHolder;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.emu.apps.qcm.spi.webmvc.rest.ApiRestMappings.IMPORTS;
import static com.emu.apps.qcm.spi.webmvc.rest.ApiRestMappings.PUBLIC_API;

@RestController
@Profile("webmvc")
@RequestMapping(value = PUBLIC_API + IMPORTS, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Import")
public class ImportRestController {

    private final ImportServicePort importServicePort;

    public ImportRestController(ImportServicePort importServicePort) {
        this.importServicePort = importServicePort;
    }

    @ResponseBody
    @GetMapping(value = "/{uuid}")
    public Upload importFileByUploadUuid(@PathVariable("uuid") String uploadUuid) throws IOException {
        return importServicePort.importFile(uploadUuid, AuthentificationContextHolder.getUser());
    }


}
