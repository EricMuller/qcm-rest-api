package com.emu.apps.qcm.rest.controllers.management.resources;


import com.emu.apps.qcm.rest.controllers.management.openui.CategoryView;
import com.emu.apps.qcm.rest.controllers.management.openui.CategoryView.CreateCategory;
import com.emu.apps.qcm.rest.controllers.management.openui.CategoryView.UpdateCategory;
import com.emu.apps.qcm.rest.controllers.management.openui.QuestionView.CreateQuestion;
import com.emu.apps.qcm.rest.controllers.management.openui.QuestionView.FindQuestion;
import com.emu.apps.qcm.rest.controllers.management.openui.QuestionView.UpdateQuestion;
import com.emu.apps.qcm.rest.controllers.management.openui.QuestionnaireView;
import com.emu.apps.qcm.rest.controllers.management.openui.QuestionView;
import com.emu.apps.qcm.rest.controllers.management.openui.QuestionnaireView.CreateQuestionnaire;
import com.emu.apps.qcm.rest.controllers.management.openui.QuestionnaireView.FindQuestionnaire;
import com.emu.apps.qcm.rest.controllers.management.openui.QuestionnaireView.UpdateQuestionnaire;
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

import static com.emu.apps.qcm.rest.controllers.management.resources.Constants.ZONED_DATE_TIME_FORMAT;

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
            FindQuestion.class, CreateQuestion.class, UpdateQuestion.class,
            FindQuestionnaire.class, CreateQuestionnaire.class, UpdateQuestionnaire.class,  })
    private String uuid;

    @JsonProperty("version")
    @JsonView({CategoryView.FindCategory.class, UpdateCategory.class, FindQuestion.class, })
    private Long version;

    @JsonProperty("dateCreation")
    @JsonView({CategoryView.FindCategory.class,
            FindQuestion.class,})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ZONED_DATE_TIME_FORMAT)
    private ZonedDateTime dateCreation;

    @JsonProperty("dateModification")
    @JsonView({CategoryView.FindCategory.class
            , FindQuestion.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ZONED_DATE_TIME_FORMAT)
    private ZonedDateTime dateModification;

    @JsonProperty("created_by")
    @JsonView({CategoryView.FindCategory.class, FindQuestionnaire.class, FindQuestion.class,})
    private String createdBy;

    @JsonProperty("lastModified_By")
    @JsonView({CategoryView.FindCategory.class, FindQuestionnaire.class, FindQuestion.class,})
    private String lastModifiedBy;

    @JsonProperty("libelle")
    @JsonView({CategoryView.FindCategory.class, UpdateCategory.class, CreateCategory.class, FindQuestion.class, })
    private String libelle;

    @JsonProperty("type")
    @JsonView({CategoryView.FindCategory.class, UpdateCategory.class, CreateCategory.class, FindQuestion.class, })
    private String type;

    @JsonIgnore
    private String userId;

}
