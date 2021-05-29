package com.emu.apps.qcm.domain.model.upload;

import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.infra.persistence.UploadPersistencePort;
import com.emu.apps.shared.exceptions.EntityNotFoundException;
import com.emu.apps.shared.exceptions.MessageSupport;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.domain.Page;
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
class UploadRepositoryAdapter implements UploadRepository {

    private final UploadPersistencePort uploadPersistencePort;

    public UploadRepositoryAdapter(UploadPersistencePort uploadPersistencePort) {
        this.uploadPersistencePort = uploadPersistencePort;
    }

    @Override
    public Upload uploadFile(String fileType,
                             MultipartFile multipartFile,
                             Boolean async,
                             PrincipalId principal) throws IOException {

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
    public Page <Upload> getUploads(Pageable pageable, PrincipalId principal) {

        return uploadPersistencePort.findAllByPage(pageable, principal.toUuid());
    }

    @Override
    public void deleteUploadByUuid(UploadId uploadId) {
        var upload = uploadPersistencePort.findByUuid(uploadId.toUuid())
                .orElseThrow(() -> new EntityNotFoundException(uploadId.toUuid(), MessageSupport.UNKNOWN_UUID_UPLOAD));

        uploadPersistencePort.deleteByUuid(upload.getUuid());

    }

    @Override
    public Upload getUploadByUuid(UploadId uploadId) {

        return  uploadPersistencePort.findByUuid(uploadId.toUuid())
                .orElseThrow(() -> new EntityNotFoundException(uploadId.toUuid(), MessageSupport.UNKNOWN_UUID_UPLOAD));


    }

    @Override
    public Upload saveUpload(Upload upload) {
        return uploadPersistencePort.saveUpload(upload);
    }

}
