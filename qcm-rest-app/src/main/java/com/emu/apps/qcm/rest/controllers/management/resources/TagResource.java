package com.emu.apps.qcm.rest.controllers.management.resources;


import com.emu.apps.qcm.rest.controllers.management.openui.QuestionView;
import com.emu.apps.qcm.rest.controllers.management.openui.QuestionnaireView;
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

    public interface Base {
    }

    public interface Update {
    }

    public interface Create {
    }

    @JsonProperty("uuid")
    @JsonView({Base.class,
            QuestionnaireView.Find.class, QuestionnaireView.Create.class, QuestionnaireView.Update.class,
            QuestionView.Find.class, QuestionView.Create.class, QuestionView.Update.class})
    private String uuid;

    @JsonProperty("libelle")
    @JsonView({Base.class, Create.class, Update.class,
            QuestionnaireView.Find.class, QuestionnaireView.Create.class, QuestionnaireView.Update.class,
            QuestionView.Find.class, QuestionView.Create.class, QuestionView.Update.class})
    private String libelle;

    @JsonProperty("version")
    @JsonView({Base.class, QuestionnaireView.Find.class, QuestionView.Find.class})
    private Long version;

    @JsonProperty("created_by")
    @JsonView({Base.class, QuestionnaireView.Find.class, QuestionView.Find.class})
    private String createdBy;

    @JsonProperty("dateCreation")
    @JsonView({Base.class, QuestionnaireView.Find.class, QuestionView.Find.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ZONED_DATE_TIME_FORMAT)
    private ZonedDateTime dateCreation;

    @JsonProperty("lastModified_By")
    @JsonView({Base.class, QuestionnaireView.Find.class, QuestionView.Find.class})
    private String lastModifiedBy;

    @JsonProperty("dateModification")
    @JsonView({Base.class, QuestionnaireView.Find.class, QuestionView.Find.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ZONED_DATE_TIME_FORMAT)
    private ZonedDateTime dateModification;


}
