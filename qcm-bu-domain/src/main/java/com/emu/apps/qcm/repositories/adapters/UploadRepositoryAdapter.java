package com.emu.apps.qcm.repositories.adapters;

import com.emu.apps.qcm.aggregates.Upload;
import com.emu.apps.qcm.repositories.UploadRepository;
import com.emu.apps.qcm.spi.persistence.UploadPersistencePort;
import com.emu.apps.qcm.spi.persistence.exceptions.MessageSupport;
import com.emu.apps.qcm.spi.persistence.exceptions.RaiseExceptionUtil;
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
public class UploadRepositoryAdapter implements UploadRepository {

    private final UploadPersistencePort uploadPersistencePort;

    public UploadRepositoryAdapter(UploadPersistencePort uploadPersistencePort) {
        this.uploadPersistencePort = uploadPersistencePort;
    }

    @Override
    public Upload uploadFile(String fileType,
                             MultipartFile multipartFile,
                             Boolean async,
                             String principal) throws IOException {

        final byte[] bytes;
        try (InputStream inputStream = multipartFile.getInputStream()) {
            bytes = inputStream.readAllBytes();
        }

        Upload uploadDto = new Upload();
        uploadDto.setFileName(FilenameUtils.getName(multipartFile.getOriginalFilename()));
        uploadDto.setContentType(multipartFile.getContentType());
        uploadDto.setData(bytes);

        return uploadPersistencePort.saveUpload(uploadDto);
    }

    @Override
    public Iterable <Upload> getUploads(Pageable pageable, String principal) {

        return uploadPersistencePort.findAllByPage(pageable, principal);
    }

    @Override
    public void deleteUploadByUuid(String uuid) {
        var uploadDto = uploadPersistencePort.findByUuid(uuid);

        RaiseExceptionUtil.raiseIfNull(uuid, uploadDto, MessageSupport.UNKNOWN_UUID_UPLOAD);

        uploadPersistencePort.deleteByUuid(uuid);

    }

    @Override
    public Upload getUploadByUuid(String uuid) {

        var uploadDto = uploadPersistencePort.findByUuid(uuid);

        RaiseExceptionUtil.raiseIfNull(uuid, uploadDto, MessageSupport.UNKNOWN_UUID_UPLOAD);

        return uploadDto;

    }

    @Override
    public Upload saveUpload(Upload upload) {
        return uploadPersistencePort.saveUpload(upload);
    }

}
