package com.emu.apps.qcm.web.dtos.export;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExportDto {

    @JsonProperty("questionnaire")
    private QuestionnaireExportDto questionnaire;

    @JsonProperty("questions")
    private List <QuestionExportDto> questions;

}


