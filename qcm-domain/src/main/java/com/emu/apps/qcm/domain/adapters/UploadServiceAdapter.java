package com.emu.apps.qcm.domain.adapters;

import com.emu.apps.qcm.domain.ports.UploadService;
import com.emu.apps.qcm.infrastructure.ports.UploadDOService;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.upload.Upload;
import com.emu.apps.qcm.infrastructure.exceptions.EntityExceptionUtil;
import com.emu.apps.qcm.infrastructure.adapters.jpa.specifications.UploadSpecificationBuilder;
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
public class UploadServiceAdapter implements UploadService {

    private final UploadDOService uploadDOService;

    private final UploadMapper uploadMapper;

    public UploadServiceAdapter(UploadDOService uploadDOService, UploadMapper uploadMapper) {
        this.uploadDOService = uploadDOService;
        this.uploadMapper = uploadMapper;
    }

    @Override
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

    @Override
    public Iterable <UploadDto> getUploads(Pageable pageable, Principal principal) {

        var uploadSpecificationBuilder = new UploadSpecificationBuilder();
        uploadSpecificationBuilder.setPrincipal(PrincipalUtils.getEmail(principal));
        return uploadMapper.pageToPageDto(uploadDOService.findAllByPage(uploadSpecificationBuilder.build(), pageable));
    }

    @Override
    public void deleteUploadById(long id) {
        var optionalUpload = uploadDOService.findById(id);

        if (!optionalUpload.isPresent()) {
            EntityExceptionUtil.raiseNoteFoundException(String.valueOf(id));
        }

        uploadDOService.deleteById(optionalUpload.get().getId());

    }

    @Override
    public UploadDto getUploadById(long id) {

        var optionalUpload = uploadDOService.findById(id);

        if (!optionalUpload.isPresent()) {
            EntityExceptionUtil.raiseNoteFoundException(String.valueOf(id));
        }
        return uploadMapper.modelToDto(optionalUpload.get());

    }

}
