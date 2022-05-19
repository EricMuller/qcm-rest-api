package com.emu.apps.qcm.rest.controllers.management.resources;

import com.emu.apps.qcm.rest.controllers.management.openui.QuestionnaireView.Create;
import com.emu.apps.qcm.rest.controllers.management.openui.QuestionnaireView.Find;
import com.emu.apps.qcm.rest.controllers.management.openui.QuestionnaireView.Update;
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

import static com.emu.apps.qcm.rest.controllers.management.resources.Constants.ZONED_DATE_TIME_FORMAT;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonRootName(value = "Questionnaire")
public class QuestionnaireResource {


    @JsonProperty("uuid")
    @JsonView({Find.class, Update.class,})
    private String uuid;

    @JsonProperty("version")
    @JsonView({Find.class, Update.class,})
    private Long version;

    @JsonProperty("created_by")
    @JsonView({Find.class})
    private String createdBy;

    @JsonProperty("dateCreation")
    @JsonView({Find.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ZONED_DATE_TIME_FORMAT)
    private ZonedDateTime dateCreation;

    @JsonProperty("lastModified_By")
    @JsonView({Find.class})
    private String lastModifiedBy;

    @JsonProperty("dateModification")
    @JsonView({Find.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ZONED_DATE_TIME_FORMAT)
    private ZonedDateTime dateModification;

    @JsonProperty("title")
    @JsonView({Find.class, Update.class, Create.class})
    private String title;

    @JsonProperty("description")
    @JsonView({Find.class, Update.class, Create.class})
    private String description;

    @JsonProperty("category")
    @JsonView({Find.class, Update.class, Create.class})
    private CategoryResource category;

    @JsonProperty("tags")
    @JsonView({Find.class, Update.class, Create.class})
    private Set <QuestionnaireTagResource> tags;

    @JsonProperty("status")
    @JsonView({Find.class, Update.class, Create.class})
    private String status;

    @JsonProperty("website")
    @JsonView({Find.class, Update.class, Create.class})
    private String website;

    @JsonProperty("published")
    @JsonView({Find.class, Update.class, Create.class})
    private Boolean published;


}
