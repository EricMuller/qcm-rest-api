package com.emu.apps.qcm.rest.controllers.management.resources;


import com.emu.apps.qcm.rest.controllers.management.openui.QuestionView.CreateQuestion;
import com.emu.apps.qcm.rest.controllers.management.openui.QuestionView.FindQuestion;
import com.emu.apps.qcm.rest.controllers.management.openui.QuestionView.UpdateQuestion;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.List;

import static com.emu.apps.qcm.rest.controllers.management.resources.Constants.ZONED_DATE_TIME_FORMAT;

/**
 * Created by eric on 05/06/2017.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@JsonRootName(value = "Question")
public class QuestionResource  {

    @JsonProperty("uuid")
    @JsonView({FindQuestion.class, UpdateQuestion.class,})
    private String uuid;

    @JsonProperty("version")
    @JsonView({FindQuestion.class, UpdateQuestion.class,})
    private Long version;

    @JsonProperty("created_by")
    @JsonView({FindQuestion.class})
    private String createdBy;

    @JsonProperty("dateCreation")
    @JsonView({FindQuestion.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ZONED_DATE_TIME_FORMAT)
    private ZonedDateTime dateCreation;

    @JsonProperty("lastModified_By")
    @JsonView({FindQuestion.class})
    private String lastModifiedBy;

    @JsonProperty("dateModification")
    @JsonView({FindQuestion.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ZONED_DATE_TIME_FORMAT)
    private ZonedDateTime dateModification;

    @JsonProperty("type")
    @JsonView({FindQuestion.class, CreateQuestion.class, UpdateQuestion.class,})
    private String type;

    @JsonProperty("question")
    @NotNull(message = "{question.notNull}")
    @Size(min = 1, max = 1024, message = "{question.question.size}")
    @JsonView({FindQuestion.class, CreateQuestion.class, UpdateQuestion.class,})
    private String questionText;

    @JsonProperty("category")
    @JsonView({FindQuestion.class, CreateQuestion.class, UpdateQuestion.class,})
    private CategoryResource category;

    @JsonProperty("responses")
    @JsonView({FindQuestion.class, CreateQuestion.class, UpdateQuestion.class,})
    private java.util.List <ResponseResource> responses;

    @JsonProperty("tags")
    @JsonView({FindQuestion.class, CreateQuestion.class, UpdateQuestion.class,})
    private List <QuestionTagResource> tags;

    @JsonProperty("status")
    @JsonView({FindQuestion.class, CreateQuestion.class, UpdateQuestion.class,})
    private String status;

    @JsonProperty("tip")
    @JsonView({FindQuestion.class, CreateQuestion.class, UpdateQuestion.class,})
    private String tip;

    @JsonProperty("owner")
    @JsonView({FindQuestion.class})
    private OwnerResource owner;

    @JsonProperty("numeroVersion")
    @JsonView({FindQuestion.class, CreateQuestion.class, UpdateQuestion.class,})
    private int numeroVersion;

}
