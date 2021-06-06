package com.emu.apps.qcm.rest.resources;

import com.emu.apps.qcm.rest.resources.openui.QuestionnaireView.Create;
import com.emu.apps.qcm.rest.resources.openui.QuestionnaireView.Find;
import com.emu.apps.qcm.rest.resources.openui.QuestionnaireView.Update;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.ZonedDateTime;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName(value = "Questionnaire")
public class QuestionnaireResource extends RepresentationModel<QuestionnaireResource>  {

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
    private ZonedDateTime dateCreation;

    @JsonProperty("lastModified_By")
    @JsonView({Find.class})
    private String lastModifiedBy;

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
    private CategoryResource category;

    @JsonProperty("tags")
    @JsonView({Find.class, Update.class, Create.class})
    private Set <TagResource> tags;

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
