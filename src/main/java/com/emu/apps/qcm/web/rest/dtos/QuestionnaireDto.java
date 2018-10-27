package com.emu.apps.qcm.web.rest.dtos;

import com.emu.apps.qcm.services.jpa.entity.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Questionnaire")
public class QuestionnaireDto extends EntityDto {

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("category")
    private CategoryDto category;

    @JsonProperty("tags")
    private Set<QuestionnaireTagDto> questionnaireTags;

    @JsonProperty("status")
    private Status status;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }

    public Set<QuestionnaireTagDto> getQuestionnaireTags() {
        return questionnaireTags;
    }

    public void setQuestionnaireTags(Set<QuestionnaireTagDto> questionnaireTags) {
        this.questionnaireTags = questionnaireTags;
    }

    public Status getStatus() {

        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
