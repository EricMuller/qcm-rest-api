package com.emu.apps.qcm.services;

import com.emu.apps.qcm.domain.UploadDOService;
import com.emu.apps.qcm.domain.entity.upload.Upload;
import com.emu.apps.qcm.domain.exceptions.EntityExceptionUtil;
import com.emu.apps.qcm.domain.jpa.specifications.UploadSpecificationBuilder;
import com.emu.apps.qcm.web.dtos.UploadDto;
import com.emu.apps.qcm.mappers.UploadMapper;
import com.emu.apps.shared.security.PrincipalUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;

/**
 * Upload Business Delegate
 * <p>
 *
 * @author eric
 * @since 2.2.0
 */
@Service
public class UploadService {

    private final UploadDOService uploadDOService;

    private final UploadMapper uploadMapper;

    public UploadService(UploadDOService uploadDOService, UploadMapper uploadMapper) {
        this.uploadDOService = uploadDOService;
        this.uploadMapper = uploadMapper;
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

        return uploadMapper.modelToDto(uploadDOService.saveUpload(upload));
    }

    public Iterable <UploadDto> getUploads(Pageable pageable, Principal principal) {

        var uploadSpecificationBuilder = new UploadSpecificationBuilder();
        uploadSpecificationBuilder.setPrincipal(PrincipalUtils.getEmail(principal));
        return uploadMapper.pageToPageDto(uploadDOService.findAllByPage(uploadSpecificationBuilder.build(), pageable));
    }

    public void deleteUploadById(long id) {
        var optionalUpload = uploadDOService.findById(id);

        if (!optionalUpload.isPresent()) {
            EntityExceptionUtil.raiseNoteFoundException(String.valueOf(id));
        }

        uploadDOService.deleteById(optionalUpload.get().getId());

    }

    public UploadDto getUploadById(long id) {

        var optionalUpload = uploadDOService.findById(id);

        if (!optionalUpload.isPresent()) {
            EntityExceptionUtil.raiseNoteFoundException(String.valueOf(id));
        }
        return uploadMapper.modelToDto(optionalUpload.get());

    }

}
