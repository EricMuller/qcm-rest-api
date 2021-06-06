package com.emu.apps.qcm.rest.resources;


import com.emu.apps.qcm.rest.resources.openui.QuestionView.Create;
import com.emu.apps.qcm.rest.resources.openui.QuestionView.Find;
import com.emu.apps.qcm.rest.resources.openui.QuestionView.Update;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.Set;

/**
 * Created by eric on 05/06/2017.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@JsonRootName(value = "Question")
public class QuestionResource extends RepresentationModel <QuestionResource> {

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

    @JsonProperty("type")
    @JsonView({Find.class, Create.class, Update.class,})
    private String type;

    @JsonProperty("question")
    @NotNull(message = "{question.notNull}")
    @Size(min = 1, max = 1024, message = "{question.question.size}")
    @JsonView({Find.class, Create.class, Update.class,})
    private String questionText;

    @JsonProperty("category")
    @JsonView({Find.class, Create.class, Update.class,})
    private CategoryResource category;

    @JsonProperty("responses")
    @JsonView({Find.class, Create.class, Update.class,})
    private java.util.List <ResponseResource> responses;

    @JsonProperty("tags")
    @JsonView({Find.class, Create.class, Update.class,})
    private Set <TagResource> tags;

    @JsonProperty("status")
    @JsonView({Find.class, Create.class, Update.class,})
    private String status;

    @JsonProperty("tip")
    @JsonView({Find.class, Create.class, Update.class,})
    private String tip;

}
