package com.emu.apps.qcm.domain.adapters;

import com.emu.apps.qcm.domain.ports.UploadServicePort;
import com.emu.apps.qcm.infrastructure.exceptions.EntityExceptionUtil;
import com.emu.apps.qcm.infrastructure.exceptions.MessageSupport;
import com.emu.apps.qcm.infrastructure.ports.UploadPersistencePort;
import com.emu.apps.qcm.domain.dtos.UploadDto;
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
public class UploadServiceAdapter implements UploadServicePort {

    private final UploadPersistencePort uploadPersistencePort;

    public UploadServiceAdapter(UploadPersistencePort uploadPersistencePort) {
        this.uploadPersistencePort = uploadPersistencePort;
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

        UploadDto uploadDto = new UploadDto();
        uploadDto.setFileName(FilenameUtils.getName(multipartFile.getOriginalFilename()));
        uploadDto.setContentType(multipartFile.getContentType());
        uploadDto.setData(bytes);

        return uploadPersistencePort.saveUpload(uploadDto);
    }

    @Override
    public Iterable <UploadDto> getUploads(Pageable pageable, Principal principal) {

        return uploadPersistencePort.findAllByPage( pageable,PrincipalUtils.getEmail(principal));
    }

    @Override
    public void deleteUploadByUuid(String uuid) {
        var uploadDto = uploadPersistencePort.findByUuid(uuid);

        EntityExceptionUtil.raiseExceptionIfNull(uuid, uploadDto, MessageSupport.UNKNOWN_UUID_UPLOAD);

        uploadPersistencePort.deleteByUuid(uuid);

    }

    @Override
    public UploadDto getUploadByUuid(String uuid) {

        var uploadDto = uploadPersistencePort.findByUuid(uuid);

        EntityExceptionUtil.raiseExceptionIfNull(uuid, uploadDto, MessageSupport.UNKNOWN_UUID_UPLOAD);

        return uploadDto;

    }

}
