package com.emu.apps.qcm.web.rest.mappers;


import com.emu.apps.qcm.services.entity.questions.Question;
import com.emu.apps.qcm.web.rest.dtos.FileQuestionDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {EpicMapper.class})
public interface FileQuestionMapper {


    Question dtoToModel(FileQuestionDto fileQuestionJson);

    List<Question> dtosToModels(List<FileQuestionDto> fileQuestionJsons);

}