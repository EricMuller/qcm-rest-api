package com.emu.apps.qcm.web.mappers;

import com.emu.apps.qcm.services.jpa.entity.upload.Upload;
import com.emu.apps.qcm.web.dtos.UploadDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring"
        , unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UploadMapper {

    UploadDto modelToDto(Upload upload);

    Upload dtoToModel(UploadDto uploadDto);

    default Page<UploadDto> pageToPageDto(Page<Upload> page) {
        return page.map(this::modelToDto);
    }
}
