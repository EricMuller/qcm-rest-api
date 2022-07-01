package com.emu.apps.qcm.rest.controllers.domain.resources;

import com.emu.apps.qcm.rest.controllers.domain.jsonview.QuestionnaireView.CreateQuestionnaire;
import com.emu.apps.qcm.rest.controllers.domain.jsonview.QuestionnaireView.FindQuestionnaire;
import com.emu.apps.qcm.rest.controllers.domain.jsonview.QuestionnaireView.UpdateQuestionnaire;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Set;

import static com.emu.apps.qcm.rest.controllers.domain.resources.DateConstants.ZONED_DATE_TIME_FORMAT;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonRootName(value = "Questionnaire")
public class QuestionnaireResource {


    @JsonProperty("uuid")
    @JsonView({FindQuestionnaire.class, UpdateQuestionnaire.class,})
    private String uuid;

    @JsonProperty("version")
    @JsonView({FindQuestionnaire.class, UpdateQuestionnaire.class,})
    private Long version;

    @JsonProperty("created_by")
    @JsonView({FindQuestionnaire.class})
    private String createdBy;

    @JsonProperty("dateCreation")
    @JsonView({FindQuestionnaire.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ZONED_DATE_TIME_FORMAT)
    private ZonedDateTime dateCreation;

    @JsonProperty("lastModified_By")
    @JsonView({FindQuestionnaire.class})
    private String lastModifiedBy;

    @JsonProperty("dateModification")
    @JsonView({FindQuestionnaire.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ZONED_DATE_TIME_FORMAT)
    private ZonedDateTime dateModification;

    @JsonProperty("title")
    @JsonView({FindQuestionnaire.class, UpdateQuestionnaire.class, CreateQuestionnaire.class})
    private String title;

    @JsonProperty("description")
    @JsonView({FindQuestionnaire.class, UpdateQuestionnaire.class, CreateQuestionnaire.class})
    private String description;

    @JsonProperty("category")
    @JsonView({FindQuestionnaire.class, UpdateQuestionnaire.class, CreateQuestionnaire.class})
    private CategoryResource category;

    @JsonProperty("tags")
    @JsonView({FindQuestionnaire.class, UpdateQuestionnaire.class, CreateQuestionnaire.class})
    private Set <QuestionnaireTagResource> tags;

    @JsonProperty("status")
    @JsonView({FindQuestionnaire.class, UpdateQuestionnaire.class, CreateQuestionnaire.class})
    private String status;

    @JsonProperty("website")
    @JsonView({FindQuestionnaire.class, UpdateQuestionnaire.class, CreateQuestionnaire.class})
    private String website;

    @JsonProperty("published")
    @JsonView({FindQuestionnaire.class, UpdateQuestionnaire.class, CreateQuestionnaire.class})
    private Boolean published;


}
