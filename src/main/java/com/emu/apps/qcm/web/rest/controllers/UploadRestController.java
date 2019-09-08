package com.emu.apps.qcm.web.rest.controllers;

import com.emu.apps.qcm.services.ImportService;
import com.emu.apps.qcm.services.UploadService;
import com.emu.apps.qcm.services.jpa.entity.upload.Upload;
import com.emu.apps.qcm.services.jpa.specifications.UploadSpecificationBuilder;
import com.emu.apps.qcm.web.rest.UploadRestApi;
import com.emu.apps.qcm.web.rest.dtos.FileQuestionDto;
import com.emu.apps.qcm.web.rest.dtos.MessageDto;
import com.emu.apps.qcm.web.rest.dtos.UploadDto;
import com.emu.apps.qcm.web.rest.mappers.UploadMapper;
import com.emu.apps.shared.web.rest.exceptions.utils.ExceptionUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.Principal;
import java.util.Optional;

@RestController
public class UploadRestController implements UploadRestApi {

    protected static final Logger LOGGER = LoggerFactory.getLogger(UploadRestController.class);

    private UploadService uploadService;

    private ImportService importService;

    private UploadMapper uploadMapper;

    @Autowired
    public UploadRestController(UploadService uploadService, UploadMapper uploadMapper, ImportService importService) {
        this.uploadService = uploadService;
        this.uploadMapper = uploadMapper;
        this.importService = importService;
    }

    @Override
    public ResponseEntity<UploadDto> uploadFile(@PathVariable("fileType") String fileType,
                                                @RequestParam("file") MultipartFile multipartFile,
                                                @RequestParam(value = "async", required = false) Boolean async,

                                                Principal principal) {
        Upload upload = new Upload();
        try {

            upload.setFileName(FilenameUtils.getBaseName(multipartFile.getOriginalFilename()));
            final byte[] bytes;
            try (InputStream inputStream = multipartFile.getInputStream()) {
                bytes = inputStream.readAllBytes();
            }
            upload.setData(bytes);
            upload = uploadService.saveUpload(upload);


        } catch (Exception e) {
            LOGGER.error("err", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(uploadMapper.modelToDto(upload), HttpStatus.CREATED);
    }

    public Iterable<UploadDto> getUploads(Pageable pageable, Principal principal) {

        UploadSpecificationBuilder uploadSpecificationBuilder = new UploadSpecificationBuilder();
        uploadSpecificationBuilder.setPrincipal(principal.getName());

        return uploadMapper.pageToPageDto(uploadService.findAllByPage(uploadSpecificationBuilder.build(), pageable));
    }


    public ResponseEntity<MessageDto> importFile(@PathVariable("id") Long uploadId, Principal principal) {

        try {

            Optional<Upload> upload = uploadService.findById(uploadId);
            ExceptionUtil.assertIsPresent(upload, String.valueOf(uploadId));
            InputStream inputStream = new ByteArrayInputStream(upload.get().getData());

            final FileQuestionDto[] fileQuestionDtos = new ObjectMapper().readValue(inputStream, FileQuestionDto[].class);
            importService.createQuestionnaires(upload.get().getFileName(), fileQuestionDtos, principal);

        } catch (Exception e) {
            LOGGER.error("err", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new MessageDto("Ok"), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Upload> deleteUploadById(long id) {
        Optional<Upload> optionalUpload = uploadService.findById(id);
        ExceptionUtil.assertIsPresent(optionalUpload, String.valueOf(id));
        uploadService.deleteById(optionalUpload.get().getId());
        return new ResponseEntity<>(optionalUpload.get(), HttpStatus.NO_CONTENT);
    }


}
