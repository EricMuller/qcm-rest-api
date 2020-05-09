package com.emu.apps.qcm.business;

import com.emu.apps.qcm.services.ImportService;
import com.emu.apps.qcm.services.UploadService;
import com.emu.apps.qcm.services.entity.upload.Upload;
import com.emu.apps.qcm.services.exceptions.EntityExceptionUtil;
import com.emu.apps.qcm.services.jpa.specifications.UploadSpecificationBuilder;
import com.emu.apps.qcm.web.dtos.UploadDto;
import com.emu.apps.qcm.web.mappers.UploadMapper;
import com.emu.apps.shared.security.PrincipalUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;

/**
 *
 * Upload Business Delegate
 *<p>
 *
 * @since 2.2.0
 * @author eric
 */
@Service
public class UploadDelegate {

    private UploadService uploadService;

    private ImportService importService;

    private UploadMapper uploadMapper;

    @Autowired
    public UploadDelegate(UploadService uploadService, UploadMapper uploadMapper, ImportService importService) {
        this.uploadService = uploadService;
        this.uploadMapper = uploadMapper;
        this.importService = importService;
    }


    public UploadDto uploadFile(String fileType,
                                MultipartFile multipartFile,
                                Boolean async,
                                Principal principal) throws IOException {

        final byte[] bytes;
        try (InputStream inputStream = multipartFile.getInputStream()) {
            bytes = inputStream.readAllBytes();
        }

        var upload = new Upload(FilenameUtils.getName(multipartFile.getOriginalFilename()), multipartFile.getContentType(), bytes);

        return uploadMapper.modelToDto(uploadService.saveUpload(upload));
    }

    public Iterable <UploadDto> getUploads(Pageable pageable, Principal principal) {

        var uploadSpecificationBuilder = new UploadSpecificationBuilder();
        uploadSpecificationBuilder.setPrincipal(PrincipalUtils.getEmail(principal));
        return uploadMapper.pageToPageDto(uploadService.findAllByPage(uploadSpecificationBuilder.build(), pageable));
    }

    public UploadDto importFile(Long uploadId, Principal principal) throws IOException {

        var optionalUpload = uploadService.findById(uploadId);
        if (!optionalUpload.isPresent()) {
            EntityExceptionUtil.raiseNoteFoundException(String.valueOf(uploadId));
        }

        return uploadMapper.modelToDto(importService.importUpload(optionalUpload.get(), principal));

    }


    public void deleteUploadById(long id) {
        var optionalUpload = uploadService.findById(id);

        if (!optionalUpload.isPresent()) {
            EntityExceptionUtil.raiseNoteFoundException(String.valueOf(id));
        }

        uploadService.deleteById(optionalUpload.get().getId());

    }


    public UploadDto getUploadById(long id) {

        var optionalUpload = uploadService.findById(id);

        if (!optionalUpload.isPresent()) {
            EntityExceptionUtil.raiseNoteFoundException(String.valueOf(id));
        }
        return uploadMapper.modelToDto(optionalUpload.get());

    }
}
