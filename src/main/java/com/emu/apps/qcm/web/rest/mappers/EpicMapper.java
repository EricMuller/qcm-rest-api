package com.emu.apps.qcm.web.rest.mappers;

import com.emu.apps.qcm.services.entity.epics.Epic;
import com.emu.apps.qcm.web.rest.dtos.EpicDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EpicMapper {

    EpicDto modelToDto(Epic epic);

    Epic dtoToModel(EpicDto epicDto);

    Iterable<EpicDto> modelsToDtos(Iterable<Epic> epics);
}