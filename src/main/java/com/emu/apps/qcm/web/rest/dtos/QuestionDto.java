package com.emu.apps.qcm.web.rest.dtos;


import com.emu.apps.qcm.services.jpa.entity.Status;
import com.emu.apps.qcm.services.jpa.entity.questions.Type;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

/**
 * Created by eric on 05/06/2017.
 */
@ApiModel(value = "Question")
public class QuestionDto extends EntityDto {

    @JsonProperty("question")
    @NotNull(message = "{question.question.notNull}")
    @Size(min = 1, max = 1024, message = "{question.question.size}")
    private String question;

    @JsonProperty("type")
    private Type type;

    @JsonProperty("status")
    private Status status;

    @JsonProperty("responses")
    private List<ResponseDto> responses;

    @JsonProperty("tags")
    private Set<QuestionTagDto> questionTags;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<ResponseDto> getResponses() {
        return responses;
    }

    public void setResponses(List<ResponseDto> responses) {
        this.responses = responses;
    }

    public Set<QuestionTagDto> getQuestionTags() {
        return questionTags;
    }

    public void setQuestionTags(Set<QuestionTagDto> questionTags) {
        this.questionTags = questionTags;
    }
}
