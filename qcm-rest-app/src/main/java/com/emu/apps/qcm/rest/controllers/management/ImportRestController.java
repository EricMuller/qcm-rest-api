package com.emu.apps.qcm.rest.controllers.management;

import com.emu.apps.qcm.application.importation.ImportService;
import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.upload.Upload;
import com.emu.apps.qcm.domain.model.upload.UploadId;
import com.emu.apps.qcm.rest.controllers.management.resources.UploadResource;
import com.emu.apps.qcm.rest.mappers.QcmResourceMapper;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.IMPORTS;
import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.MANAGEMENT_API;
import static com.emu.apps.shared.security.AccountContextHolder.getPrincipal;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = MANAGEMENT_API + IMPORTS, produces = APPLICATION_JSON_VALUE)
@Tag(name = "Import")
@Timed("imports")
@Slf4j
public class ImportRestController {

    private final ImportService importService;

    private final QcmResourceMapper qcmResourceMapper;

    public ImportRestController(ImportService importService, QcmResourceMapper qcmResourceMapper) {
        this.importService = importService;
        this.qcmResourceMapper = qcmResourceMapper;
    }

    @ResponseBody
    @GetMapping(value = "/{uuid}")
    @Timed(value = "imports.importFileByUploadUuid", longTask = true)
    public UploadResource importFileByUploadUuid(@PathVariable("uuid") String uploadUuid) throws IOException, ExecutionException, InterruptedException {


        LOGGER.warn("CPU Core: {} " , Runtime.getRuntime().availableProcessors());
        LOGGER.warn("CommonPool Parallelism: {}" , ForkJoinPool.commonPool().getParallelism());
        LOGGER.warn("CommonPool Common Parallelism: {}" , ForkJoinPool.getCommonPoolParallelism());

        Future <Upload> uploadCompletableFuture = importService.importFileAsync(new UploadId(uploadUuid), PrincipalId.of(getPrincipal()));

        var upload = uploadCompletableFuture.get();

        return qcmResourceMapper.uploadToUploadResources(upload);
    }

}
