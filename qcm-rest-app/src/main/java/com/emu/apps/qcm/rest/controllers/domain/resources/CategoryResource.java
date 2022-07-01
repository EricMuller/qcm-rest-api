package com.emu.apps.qcm.rest.controllers.domain.resources;


import com.emu.apps.qcm.rest.controllers.domain.jsonview.CategoryView;
import com.emu.apps.qcm.rest.controllers.domain.jsonview.CategoryView.CreateCategory;
import com.emu.apps.qcm.rest.controllers.domain.jsonview.CategoryView.UpdateCategory;
import com.emu.apps.qcm.rest.controllers.domain.jsonview.QuestionView.CreateQuestion;
import com.emu.apps.qcm.rest.controllers.domain.jsonview.QuestionView.GetQuestion;
import com.emu.apps.qcm.rest.controllers.domain.jsonview.QuestionView.UpdateQuestion;
import com.emu.apps.qcm.rest.controllers.domain.jsonview.QuestionnaireView.CreateQuestionnaire;
import com.emu.apps.qcm.rest.controllers.domain.jsonview.QuestionnaireView.FindQuestionnaire;
import com.emu.apps.qcm.rest.controllers.domain.jsonview.QuestionnaireView.UpdateQuestionnaire;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

import static com.emu.apps.qcm.rest.controllers.domain.resources.DateConstants.ZONED_DATE_TIME_FORMAT;

/**
 * Created by eric on 05/06/2017.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName(value = "Category")
public class CategoryResource {

    @JsonProperty("uuid")
    @JsonView({CategoryView.FindCategory.class, UpdateCategory.class,
            GetQuestion.class, CreateQuestion.class, UpdateQuestion.class,
            FindQuestionnaire.class, CreateQuestionnaire.class, UpdateQuestionnaire.class,  })
    private String uuid;

    @JsonProperty("version")
    @JsonView({CategoryView.FindCategory.class, UpdateCategory.class, GetQuestion.class, })
    private Long version;

    @JsonProperty("dateCreation")
    @JsonView({CategoryView.FindCategory.class,
            GetQuestion.class,})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ZONED_DATE_TIME_FORMAT)
    private ZonedDateTime dateCreation;

    @JsonProperty("dateModification")
    @JsonView({CategoryView.FindCategory.class
            , GetQuestion.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ZONED_DATE_TIME_FORMAT)
    private ZonedDateTime dateModification;

    @JsonProperty("created_by")
    @JsonView({CategoryView.FindCategory.class, FindQuestionnaire.class, GetQuestion.class,})
    private String createdBy;

    @JsonProperty("lastModified_By")
    @JsonView({CategoryView.FindCategory.class, FindQuestionnaire.class, GetQuestion.class,})
    private String lastModifiedBy;

    @JsonProperty("libelle")
    @JsonView({CategoryView.FindCategory.class, UpdateCategory.class, CreateCategory.class, GetQuestion.class, })
    private String libelle;

    @JsonProperty("type")
    @JsonView({CategoryView.FindCategory.class, UpdateCategory.class, CreateCategory.class, GetQuestion.class, })
    private String type;

    @JsonIgnore
    private String userId;

}
