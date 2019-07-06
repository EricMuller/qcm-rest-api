package com.emu.apps.qcm.web.rest.mappers;


import com.emu.apps.qcm.services.jpa.entity.questions.Question;
import com.emu.apps.qcm.web.rest.dtos.FileQuestionDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class}
        ,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FileQuestionMapper {


    Question dtoToModel(FileQuestionDto fileQuestionJson);

    List<Question> dtosToModels(List<FileQuestionDto> fileQuestionJsons);

}
