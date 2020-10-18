package com.emu.apps.qcm.spi.infrastructure;

import com.emu.apps.qcm.api.models.Questionnaire;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

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
