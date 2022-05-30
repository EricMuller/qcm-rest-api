package com.emu.apps.qcm.application.export.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
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
@JsonRootName(value = "Export")
public class Export {

    private  static final String VERSION = "1.0";

    @JsonProperty("name")
    private String name;

    @JsonProperty("version")
    private String versionExport = VERSION;

    @JsonProperty("questionnaire")
    private QuestionnaireExport questionnaire;

    @JsonProperty("questions")
    private List <QuestionExport> questions;

}


