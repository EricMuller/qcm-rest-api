package com.emu.apps.qcm.rest.controllers.management;

import com.emu.apps.qcm.application.importation.ImportService;
import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.upload.UploadId;
import com.emu.apps.qcm.rest.controllers.management.resources.UploadResource;
import com.emu.apps.qcm.rest.mappers.QuestionnaireResourceMapper;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.IMPORTS;
import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.MANAGEMENT_API;
import static com.emu.apps.shared.security.AuthentificationContextHolder.getPrincipal;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = MANAGEMENT_API + IMPORTS, produces = APPLICATION_JSON_VALUE)
@Tag(name = "Import")
@Timed("imports")
public class ImportRestController {

    private final ImportService importService;

    private final QuestionnaireResourceMapper questionnaireResourceMapper;

    public ImportRestController(ImportService importService, QuestionnaireResourceMapper questionnaireResourceMapper) {
        this.importService = importService;
        this.questionnaireResourceMapper = questionnaireResourceMapper;
    }

    @ResponseBody
    @GetMapping(value = "/{uuid}")
    @Timed(value = "imports.importFileByUploadUuid", longTask = true)
    public UploadResource importFileByUploadUuid(@PathVariable("uuid") String uploadUuid) throws IOException {
        return questionnaireResourceMapper.uploadToResources(importService.importFile(new UploadId(uploadUuid), PrincipalId.of(getPrincipal())));
    }

}