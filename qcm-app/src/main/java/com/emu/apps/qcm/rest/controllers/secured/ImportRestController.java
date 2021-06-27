package com.emu.apps.qcm.rest.controllers.secured;

import com.emu.apps.qcm.application.importation.ImportService;
import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.upload.UploadId;
import com.emu.apps.qcm.rest.mappers.QuestionnaireResourceMapper;
import com.emu.apps.qcm.rest.controllers.secured.resources.UploadResource;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.IMPORTS;
import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.PROTECTED_API;
import static com.emu.apps.shared.security.AuthentificationContextHolder.getPrincipal;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Profile("webmvc")
@RequestMapping(value = PROTECTED_API + IMPORTS, produces = APPLICATION_JSON_VALUE)
@Tag(name = "Import")
public class ImportRestController {

    private final ImportService importService;

    private final QuestionnaireResourceMapper questionnaireResourceMapper;

    public ImportRestController(ImportService importService, QuestionnaireResourceMapper questionnaireResourceMapper) {
        this.importService = importService;
        this.questionnaireResourceMapper = questionnaireResourceMapper;
    }

    @ResponseBody
    @GetMapping(value = "/{uuid}")
    public UploadResource importFileByUploadUuid(@PathVariable("uuid") String uploadUuid) throws IOException {
        return questionnaireResourceMapper.uploadToResources(importService.importFile(new UploadId(uploadUuid), new PrincipalId(getPrincipal())));
    }

}
