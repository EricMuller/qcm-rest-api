package com.emu.apps.qcm.domain.adapters;

import com.emu.apps.qcm.models.UploadDto;
import com.emu.apps.qcm.domain.ports.UploadServicePort;
import com.emu.apps.qcm.infrastructure.exceptions.RaiseExceptionUtil;
import com.emu.apps.qcm.infrastructure.exceptions.MessageSupport;
import com.emu.apps.qcm.infrastructure.ports.UploadPersistencePort;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

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
                                String principal) throws IOException {

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
    public Iterable <UploadDto> getUploads(Pageable pageable, String principal) {

        return uploadPersistencePort.findAllByPage(pageable, principal);
    }

    @Override
    public void deleteUploadByUuid(String uuid) {
        var uploadDto = uploadPersistencePort.findByUuid(uuid);

        RaiseExceptionUtil.raiseIfNull(uuid, uploadDto, MessageSupport.UNKNOWN_UUID_UPLOAD);

        uploadPersistencePort.deleteByUuid(uuid);

    }

    @Override
    public UploadDto getUploadByUuid(String uuid) {

        var uploadDto = uploadPersistencePort.findByUuid(uuid);

        RaiseExceptionUtil.raiseIfNull(uuid, uploadDto, MessageSupport.UNKNOWN_UUID_UPLOAD);

        return uploadDto;

    }

}
