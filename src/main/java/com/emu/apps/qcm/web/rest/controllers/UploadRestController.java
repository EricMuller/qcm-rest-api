package com.emu.apps.qcm.web.rest.controllers;

import com.emu.apps.qcm.services.UploadService;
import com.emu.apps.qcm.web.rest.dtos.FileQuestionDto;
import com.emu.apps.qcm.web.rest.dtos.MessageDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/api/v1/upload")
@Api(value = "upload-store", description = "All operations ", tags = "Upload")
public class UploadRestController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UploadService uploadService;

    @ApiOperation(value = "upload a file", responseContainer = "ResponseEntity", response = MessageDto.class, tags = "Upload", nickname = "uploadFile")
    @ResponseBody
    @Secured("ROLE_USER")
    @RequestMapping(value = "/{fileType}", method = RequestMethod.POST, headers = "Content-Type=multipart/form-data", produces = "application/json")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("fileType") String fileType, Principal principal) throws IOException {
        try {
            logger.info(file.getOriginalFilename());
            ObjectMapper objectMapper = new ObjectMapper();
            final FileQuestionDto[] fileQuestionDtos = objectMapper.readValue(file.getInputStream(), FileQuestionDto[].class);
            String name = FilenameUtils.getBaseName(file.getOriginalFilename());

            uploadService.createQuestionnaires(name, fileQuestionDtos, principal);

        } catch (Exception e) {
            logger.error("err", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new MessageDto("Ok"), HttpStatus.OK);
    }


}
