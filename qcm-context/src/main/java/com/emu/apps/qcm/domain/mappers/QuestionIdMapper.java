package com.emu.apps.qcm.domain.mappers;

import com.emu.apps.qcm.domain.model.question.QuestionId;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuestionIdMapper {

    default QuestionId toQuestionId(UUID uuid) {
        return new QuestionId(uuid.toString());
    }

    default QuestionId toQuestionId(String uuid) {
        return new QuestionId(uuid);
    }

    default String toUuid(QuestionId id) {
        return id.toUuid();
    }
}
