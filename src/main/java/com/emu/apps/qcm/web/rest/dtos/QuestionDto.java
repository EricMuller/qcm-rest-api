package com.emu.apps.qcm.web.rest.dtos;


import com.emu.apps.qcm.services.entity.questions.Type;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

import java.util.List;
import java.util.Set;

/**
 * Created by eric on 05/06/2017.
 */
@ApiModel(value = "Question")
public class QuestionDto extends EntityDto {

    private String question;

    private Type type;

    private List<ResponseDto> responses;

    @JsonProperty("tags")
    private Set<QuestionTagDto> questionTags;


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<ResponseDto> getResponses() {
        return responses;
    }

    public void setResponses(List<ResponseDto> responses) {
        this.responses = responses;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Set<QuestionTagDto> getQuestionTags() {
        return questionTags;
    }

    public void setQuestionTags(Set<QuestionTagDto> questionTags) {
        this.questionTags = questionTags;
    }
}
