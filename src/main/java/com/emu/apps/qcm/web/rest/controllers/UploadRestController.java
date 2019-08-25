package com.emu.apps.qcm.web.rest.controllers;

import com.emu.apps.qcm.services.FileImportService;
import com.emu.apps.qcm.services.FileStoreService;
import com.emu.apps.qcm.web.rest.UploadRestApi;
import com.emu.apps.qcm.web.rest.dtos.FileQuestionDto;
import com.emu.apps.qcm.web.rest.dtos.MessageDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
public class UploadRestController implements UploadRestApi {

    protected static final Logger LOGGER = LoggerFactory.getLogger(UploadRestController.class);

    private final FileImportService fileImportService;

    private FileStoreService fileStoreService;

    @Autowired
    public UploadRestController(FileImportService uploadService, FileStoreService storageService) {
        this.fileImportService = uploadService;
        this.fileStoreService = storageService;
    }

    @Override
    public ResponseEntity<MessageDto> uploadFile(@PathVariable("fileType") String fileType,
                                          @RequestParam("file") MultipartFile file,
                                          @RequestParam(value = "async", required = false) Boolean async,
                                          Principal principal) throws IOException {
        try {

            String name = FilenameUtils.getBaseName(file.getOriginalFilename());

            // File file1 = fileStoreService.store(file.getInputStream(), name);

            if (BooleanUtils.isNotTrue(async)) {
                final FileQuestionDto[] fileQuestionDtos = new ObjectMapper().readValue(file.getInputStream(), FileQuestionDto[].class);
                fileImportService.createQuestionnaires(name, fileQuestionDtos, principal);
            }

        } catch (Exception e) {
            LOGGER.error("err", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new MessageDto("Ok"), HttpStatus.CREATED);
    }


}
