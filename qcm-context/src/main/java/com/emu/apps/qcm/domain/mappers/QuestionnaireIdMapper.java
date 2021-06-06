package com.emu.apps.qcm.domain.mappers;

import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireId;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuestionnaireIdMapper {

    default QuestionnaireId toQuestionnaireId(UUID uuid) {
        return new QuestionnaireId(uuid.toString());
    }

    default QuestionnaireId toQuestionnaireId(String uuid) {
        return new QuestionnaireId(uuid);
    }

    default String toUuid(QuestionnaireId id) {
        return id.toUuid();
    }

    String[] toUuid(QuestionnaireId[] id);

    QuestionnaireId[] toQuestionnaireId(String[] id);
}
