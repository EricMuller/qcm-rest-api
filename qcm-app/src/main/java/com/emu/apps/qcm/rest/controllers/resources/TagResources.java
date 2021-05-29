package com.emu.apps.qcm.rest.controllers.resources;


import com.emu.apps.qcm.rest.controllers.resources.openui.QuestionView;
import com.emu.apps.qcm.rest.controllers.resources.openui.QuestionnaireView;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

/**
 * Created by eric on 05/06/2017.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName(value = "Tag")
public class TagResources {

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
    @JsonView(Base.class)
    private Long version;

    @JsonProperty("dateCreation")
    @JsonView(Base.class)
    private ZonedDateTime dateCreation;

    @JsonProperty("dateModification")
    @JsonView(Base.class)
    private ZonedDateTime dateModification;


}
