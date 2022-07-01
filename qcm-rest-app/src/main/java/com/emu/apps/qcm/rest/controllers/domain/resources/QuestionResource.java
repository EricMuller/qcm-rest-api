package com.emu.apps.qcm.rest.controllers.domain.resources;


import com.emu.apps.qcm.rest.controllers.domain.jsonview.QuestionView.CreateQuestion;
import com.emu.apps.qcm.rest.controllers.domain.jsonview.QuestionView.GetQuestion;
import com.emu.apps.qcm.rest.controllers.domain.jsonview.QuestionView.UpdateQuestion;
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

import static com.emu.apps.qcm.rest.controllers.domain.resources.DateConstants.ZONED_DATE_TIME_FORMAT;

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
    @JsonView({GetQuestion.class, UpdateQuestion.class,})
    private String uuid;

    @JsonProperty("version")
    @JsonView({GetQuestion.class, UpdateQuestion.class,})
    private Long version;

    @JsonProperty("created_by")
    @JsonView({GetQuestion.class})
    private String createdBy;

    @JsonProperty("dateCreation")
    @JsonView({GetQuestion.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ZONED_DATE_TIME_FORMAT)
    private ZonedDateTime dateCreation;

    @JsonProperty("lastModified_By")
    @JsonView({GetQuestion.class})
    private String lastModifiedBy;

    @JsonProperty("dateModification")
    @JsonView({GetQuestion.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ZONED_DATE_TIME_FORMAT)
    private ZonedDateTime dateModification;

    @JsonProperty("type")
    @JsonView({GetQuestion.class, CreateQuestion.class, UpdateQuestion.class,})
    private String type;

    @JsonProperty("question")
    @NotNull(message = "{question.notNull}")
    @Size(min = 1, max = 1024, message = "{question.question.size}")
    @JsonView({GetQuestion.class, CreateQuestion.class, UpdateQuestion.class,})
    private String questionText;

    @JsonProperty("category")
    @JsonView({GetQuestion.class, CreateQuestion.class, UpdateQuestion.class,})
    private CategoryResource category;

    @JsonProperty("responses")
    @JsonView({GetQuestion.class, CreateQuestion.class, UpdateQuestion.class,})
    private java.util.List <ResponseResource> responses;

    @JsonProperty("tags")
    @JsonView({GetQuestion.class, CreateQuestion.class, UpdateQuestion.class,})
    private List <QuestionTagResource> tags;

    @JsonProperty("status")
    @JsonView({GetQuestion.class, CreateQuestion.class, UpdateQuestion.class,})
    private String status;

    @JsonProperty("tip")
    @JsonView({GetQuestion.class, CreateQuestion.class, UpdateQuestion.class,})
    private String tip;

    @JsonProperty("owner")
    @JsonView({GetQuestion.class})
    private OwnerResource owner;

    @JsonProperty("numeroVersion")
    @JsonView({GetQuestion.class, CreateQuestion.class, UpdateQuestion.class,})
    private int numeroVersion;

}
