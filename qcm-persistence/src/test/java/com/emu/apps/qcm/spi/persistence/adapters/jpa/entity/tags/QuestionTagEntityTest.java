package com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags;

import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags.QuestionTagId;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questions.QuestionEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTagEntityTest {
private QuestionTagEntity pojoObject;
public QuestionTagEntityTest() {
this.pojoObject = new com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags.QuestionTagEntity();
}
@Test
void testId() {
QuestionTagId param = new QuestionTagId();
pojoObject.setId(param);
Object result = pojoObject.getId();
assertEquals(param, result);
}

@Test
void testQuestion() {
QuestionEntity param = new QuestionEntity();
pojoObject.setQuestion(param);
Object result = pojoObject.getQuestion();
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

