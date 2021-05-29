package com.emu.apps.qcm.rest.controllers.resources;

import com.emu.apps.qcm.rest.controllers.resources.openui.QuestionnaireView.Create;
import com.emu.apps.qcm.rest.controllers.resources.openui.QuestionnaireView.Find;
import com.emu.apps.qcm.rest.controllers.resources.openui.QuestionnaireView.Update;
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

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName(value = "Questionnaire")
public class QuestionnaireResources {

    @JsonProperty("uuid")
    @JsonView({Find.class, Update.class,})
    private String uuid;

    @JsonProperty("version")
    @JsonView({Find.class, Update.class,})
    private Long version;

    @JsonProperty("dateCreation")
    @JsonView({Find.class})
    private ZonedDateTime dateCreation;

    @JsonProperty("dateModification")
    @JsonView({Find.class})
    private ZonedDateTime dateModification;

    @JsonProperty("title")
    @JsonView({Find.class, Update.class, Create.class})
    private String title;

    @JsonProperty("description")
    @JsonView({Find.class, Update.class, Create.class})
    private String description;

    @JsonProperty("category")
    @JsonView({Find.class, Update.class, Create.class})
    private CategoryResources category;

    @JsonProperty("tags")
    @JsonView({Find.class, Update.class, Create.class})
    private Set <TagResources> tags;

    @JsonProperty("status")
    @JsonView({Find.class, Update.class, Create.class})
    private String status;

    @JsonProperty("website")
    @JsonView({Find.class, Update.class, Create.class})
    private String website;

    @JsonProperty("published")
    @JsonView({Find.class, Update.class, Create.class})
    private Boolean published;

    @JsonProperty("created_by")
    @JsonView({Find.class})
    private String createdBy;

}
