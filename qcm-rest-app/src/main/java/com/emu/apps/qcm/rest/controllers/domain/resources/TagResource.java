package com.emu.apps.qcm.rest.controllers.domain.resources;


import com.emu.apps.qcm.rest.controllers.domain.jsonview.QuestionView.CreateQuestion;
import com.emu.apps.qcm.rest.controllers.domain.jsonview.QuestionView.GetQuestion;
import com.emu.apps.qcm.rest.controllers.domain.jsonview.QuestionView.UpdateQuestion;
import com.emu.apps.qcm.rest.controllers.domain.jsonview.QuestionnaireView.CreateQuestionnaire;
import com.emu.apps.qcm.rest.controllers.domain.jsonview.QuestionnaireView.FindQuestionnaire;
import com.emu.apps.qcm.rest.controllers.domain.jsonview.QuestionnaireView.UpdateQuestionnaire;
import com.fasterxml.jackson.annotation.JsonFormat;
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
            GetQuestion.class, CreateQuestion.class, UpdateQuestion.class})
    private String uuid;

    @JsonProperty("libelle")
    @JsonView({BaseTag.class, CreateTag.class, UpdateTag.class,
            FindQuestionnaire.class, CreateQuestionnaire.class, UpdateQuestionnaire.class,
            GetQuestion.class, CreateQuestion.class, UpdateQuestion.class})
    private String libelle;

    @JsonProperty("version")
    @JsonView({BaseTag.class, FindQuestionnaire.class, GetQuestion.class})
    private Long version;

    @JsonProperty("created_by")
    @JsonView({BaseTag.class, FindQuestionnaire.class, GetQuestion.class})
    private String createdBy;

    @JsonProperty("dateCreation")
    @JsonView({BaseTag.class, FindQuestionnaire.class, GetQuestion.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ZONED_DATE_TIME_FORMAT)
    private ZonedDateTime dateCreation;

    @JsonProperty("lastModified_By")
    @JsonView({BaseTag.class, FindQuestionnaire.class, GetQuestion.class})
    private String lastModifiedBy;

    @JsonProperty("dateModification")
    @JsonView({BaseTag.class, FindQuestionnaire.class, GetQuestion.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ZONED_DATE_TIME_FORMAT)
    private ZonedDateTime dateModification;


}
