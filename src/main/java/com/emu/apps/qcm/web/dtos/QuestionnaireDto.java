package com.emu.apps.qcm.web.dtos;

import com.emu.apps.qcm.services.jpa.entity.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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



}
