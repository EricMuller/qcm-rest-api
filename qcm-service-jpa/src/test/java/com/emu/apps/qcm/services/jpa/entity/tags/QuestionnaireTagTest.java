package com.emu.apps.qcm.services.jpa.entity.tags;

import com.emu.apps.qcm.services.jpa.entity.tags.QuestionnaireTagId;
import com.emu.apps.qcm.services.jpa.entity.questionnaires.Questionnaire;
import com.emu.apps.qcm.services.jpa.entity.tags.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuestionnaireTagTest {
private QuestionnaireTag pojoObject;
public QuestionnaireTagTest() throws Exception {
this.pojoObject = new com.emu.apps.qcm.services.jpa.entity.tags.QuestionnaireTag();
}
@Test
public void testId() {
QuestionnaireTagId param = new QuestionnaireTagId();
pojoObject.setId(param);
Object result = pojoObject.getId();
assertEquals(param, result);
}

@Test
public void testQuestionnaire() {
Questionnaire param = new Questionnaire();
pojoObject.setQuestionnaire(param);
Object result = pojoObject.getQuestionnaire();
assertEquals(param, result);
}

@Test
public void testTag() {
Tag param = new Tag();
pojoObject.setTag(param);
Object result = pojoObject.getTag();
assertEquals(param, result);
}

}

