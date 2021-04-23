package com.emu.apps.qcm.infra.persistence.adapters.jpa.fixtures;

import com.emu.apps.qcm.domain.model.questionnaire.Questionnaire;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@NoArgsConstructor
public final class ModelFixture extends Fixture {


    public Questionnaire createQuestionaire() {

        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setTitle(QUESTIONNAIRE_TITLE);
        questionnaire.setDescription(QUESTIONNAIRE_DESC);


        return questionnaire;

    }


}
