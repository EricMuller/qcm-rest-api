package com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags;

import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags.QuestionnaireTagId;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questionnaires.QuestionnaireEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionnaireTagEntityTest {
private QuestionnaireTagEntity pojoObject;
public QuestionnaireTagEntityTest() {
this.pojoObject = new com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags.QuestionnaireTagEntity();
}
@Test
void testId() {
QuestionnaireTagId param = new QuestionnaireTagId();
pojoObject.setId(param);
Object result = pojoObject.getId();
assertEquals(param, result);
}

@Test
void testQuestionnaire() {
QuestionnaireEntity param = new QuestionnaireEntity();
pojoObject.setQuestionnaire(param);
Object result = pojoObject.getQuestionnaire();
assertEquals(param, result);
}

@Test
void testTag() {
Tag param = new Tag();
pojoObject.setTag(param);
Object result = pojoObject.getTag();
assertEquals(param, result);
}

}

