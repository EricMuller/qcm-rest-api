package com.emu.apps.qcm.web.dtos.question;


import com.emu.apps.qcm.web.dtos.QuestionTagDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

import java.util.Set;

/**
 * Created by eric on 05/06/2017.
 */
@ApiModel(value = "QuestionTags")
public class QuestionTagsDto extends QuestionLightDto {

    @JsonProperty("tags")
    private Set<QuestionTagDto> questionTags;

    public Set<QuestionTagDto> getQuestionTags() {
        return questionTags;
    }

    public void setQuestionTags(Set<QuestionTagDto> questionTags) {
        this.questionTags = questionTags;
    }
}
