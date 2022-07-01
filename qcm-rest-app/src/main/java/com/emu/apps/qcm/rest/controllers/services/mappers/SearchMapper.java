package com.emu.apps.qcm.rest.controllers.services.mappers;

import com.emu.apps.qcm.domain.mappers.QuestionIdMapper;
import com.emu.apps.qcm.domain.query.question.QuestionWithTagsOnly;
import com.emu.apps.qcm.rest.controllers.domain.mappers.ResourceId;
import com.emu.apps.qcm.rest.controllers.services.search.SearchQuestionsByTagAndQcmId;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring",
        uses = {QuestionIdMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SearchMapper {

    @ResourceId
    SearchQuestionsByTagAndQcmId questionTagsToSearchResources(QuestionWithTagsOnly questionWithTagsOnly);

    default Page <SearchQuestionsByTagAndQcmId> pageQuestionTagsToResources(Page <QuestionWithTagsOnly> page) {
        return page.map(this::questionTagsToSearchResources);
    }

}
