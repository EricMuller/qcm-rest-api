package com.emu.apps.qcm.rest.controllers;

import com.emu.apps.qcm.application.ImportServices;
import com.emu.apps.qcm.domain.model.upload.Upload;

import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.upload.UploadId;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.IMPORTS;
import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.PUBLIC_API;
import static com.emu.apps.shared.security.AuthentificationContextHolder.getPrincipal;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Profile("webmvc")
@RequestMapping(value = PUBLIC_API + IMPORTS, produces = APPLICATION_JSON_VALUE)
@Tag(name = "Import")
public class ImportRestController {

    private final ImportServices importServices;

    public ImportRestController(ImportServices importServices) {
        this.importServices = importServices;
    }

    @ResponseBody
    @GetMapping(value = "/{uuid}")
    public Upload importFileByUploadUuid(@PathVariable("uuid") String uploadUuid) throws IOException {
        return importServices.importFile(new UploadId(uploadUuid), new PrincipalId(getPrincipal()));
    }

}
