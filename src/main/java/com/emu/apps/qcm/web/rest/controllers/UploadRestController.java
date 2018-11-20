package com.emu.apps.qcm.web.rest.controllers;

import com.emu.apps.qcm.services.UploadService;
import com.emu.apps.qcm.web.rest.UploadRestApi;
import com.emu.apps.qcm.web.rest.dtos.FileQuestionDto;
import com.emu.apps.qcm.web.rest.dtos.MessageDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FilenameUtils;
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

    private final UploadService uploadService;

    @Autowired
    public UploadRestController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @Override
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("fileType") String fileType, Principal principal) throws IOException {
        try {
            LOGGER.info(file.getOriginalFilename());
            ObjectMapper objectMapper = new ObjectMapper();
            final FileQuestionDto[] fileQuestionDtos = objectMapper.readValue(file.getInputStream(), FileQuestionDto[].class);
            String name = FilenameUtils.getBaseName(file.getOriginalFilename());

            uploadService.createQuestionnaires(name, fileQuestionDtos, principal);

        } catch (Exception e) {
            LOGGER.error("err", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new MessageDto("Ok"), HttpStatus.OK);
    }


}
