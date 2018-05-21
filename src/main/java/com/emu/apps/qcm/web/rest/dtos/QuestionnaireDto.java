package com.emu.apps.qcm.web.rest.dtos;

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

    @JsonProperty("epic")
    private CategoryDto epic;

    @JsonProperty("tags")
    private Set<QuestionnaireTagDto> questionnaireTags;

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

    public CategoryDto getEpic() {
        return epic;
    }

    public void setEpic(CategoryDto epic) {
        this.epic = epic;
    }

    public Set<QuestionnaireTagDto> getQuestionnaireTags() {
        return questionnaireTags;
    }

    public void setQuestionnaireTags(Set<QuestionnaireTagDto> questionnaireTags) {
        this.questionnaireTags = questionnaireTags;
    }
}
