package com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questionnaires;

import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questionnaires.QuestionnaireQuestionId;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questionnaires.QuestionnaireEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questions.QuestionEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionnaireQuestionEntityTest {
private QuestionnaireQuestionEntity pojoObject;
public QuestionnaireQuestionEntityTest() {
this.pojoObject = new com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questionnaires.QuestionnaireQuestionEntity();
}
@Test
void testId() {
QuestionnaireQuestionId param = new QuestionnaireQuestionId();
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
void testQuestion() {
QuestionEntity param = new QuestionEntity();
pojoObject.setQuestion(param);
Object result = pojoObject.getQuestion();
assertEquals(param, result);
}

@Test
void testPosition() {
Integer param = Integer.valueOf(123);
pojoObject.setPosition(param);
Object result = pojoObject.getPosition();
assertEquals(param, result);
}

@Test
void testPoints() {
Integer param = Integer.valueOf(123);
pojoObject.setPoints(param);
Object result = pojoObject.getPoints();
assertEquals(param, result);
}

}

