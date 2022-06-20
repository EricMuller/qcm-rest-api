package com.emu.apps.qcm.rest.controllers.management.resources;


import com.emu.apps.qcm.rest.controllers.management.openui.QuestionView.CreateQuestion;
import com.emu.apps.qcm.rest.controllers.management.openui.QuestionView.FindQuestion;
import com.emu.apps.qcm.rest.controllers.management.openui.QuestionView.UpdateQuestion;
import com.emu.apps.qcm.rest.controllers.management.openui.QuestionnaireView;
import com.emu.apps.qcm.rest.controllers.management.openui.QuestionnaireView.CreateQuestionnaire;
import com.emu.apps.qcm.rest.controllers.management.openui.QuestionnaireView.FindQuestionnaire;
import com.emu.apps.qcm.rest.controllers.management.openui.QuestionnaireView.UpdateQuestionnaire;
import com.fasterxml.jackson.annotation.JsonFormat;
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
@JsonRootName(value = "Tag")
public class TagResource {

    public interface BaseTag {
    }

    public interface UpdateTag {
    }

    public interface CreateTag {
    }

    @JsonProperty("uuid")
    @JsonView({BaseTag.class,
            FindQuestionnaire.class, CreateQuestionnaire.class, UpdateQuestionnaire.class,
            FindQuestion.class, CreateQuestion.class, UpdateQuestion.class})
    private String uuid;

    @JsonProperty("libelle")
    @JsonView({BaseTag.class, CreateTag.class, UpdateTag.class,
            FindQuestionnaire.class, CreateQuestionnaire.class, UpdateQuestionnaire.class,
            FindQuestion.class, CreateQuestion.class, UpdateQuestion.class})
    private String libelle;

    @JsonProperty("version")
    @JsonView({BaseTag.class, FindQuestionnaire.class, FindQuestion.class})
    private Long version;

    @JsonProperty("created_by")
    @JsonView({BaseTag.class, FindQuestionnaire.class, FindQuestion.class})
    private String createdBy;

    @JsonProperty("dateCreation")
    @JsonView({BaseTag.class, FindQuestionnaire.class, FindQuestion.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ZONED_DATE_TIME_FORMAT)
    private ZonedDateTime dateCreation;

    @JsonProperty("lastModified_By")
    @JsonView({BaseTag.class, FindQuestionnaire.class, FindQuestion.class})
    private String lastModifiedBy;

    @JsonProperty("dateModification")
    @JsonView({BaseTag.class, FindQuestionnaire.class, FindQuestion.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ZONED_DATE_TIME_FORMAT)
    private ZonedDateTime dateModification;


}
