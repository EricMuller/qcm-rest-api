package com.emu.apps.qcm.domain.model.questionnaire;

import com.emu.apps.qcm.domain.model.base.PrincipalId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionnaireQuery {

    Page <Questionnaire> getQuestionnaires(String[] tagUuid, Pageable pageable, PrincipalId principal);


}
