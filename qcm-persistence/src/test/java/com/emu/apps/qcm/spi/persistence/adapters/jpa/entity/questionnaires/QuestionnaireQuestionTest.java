package com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questionnaires;

import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questions.QuestionEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionnaireQuestionTest {
private QuestionnaireQuestionEntity pojoObject;
public QuestionnaireQuestionTest() throws Exception {
this.pojoObject = new QuestionnaireQuestionEntity();
}
@Test
public void testId() {
QuestionnaireQuestionId param = new QuestionnaireQuestionId();
pojoObject.setId(param);
Object result = pojoObject.getId();
assertEquals(param, result);
}

@Test
public void testQuestionnaire() {
QuestionnaireEntity param = new QuestionnaireEntity();
pojoObject.setQuestionnaire(param);
Object result = pojoObject.getQuestionnaire();
assertEquals(param, result);
}

@Test
public void testQuestion() {
QuestionEntity param = new QuestionEntity();
pojoObject.setQuestion(param);
Object result = pojoObject.getQuestion();
assertEquals(param, result);
}

@Test
public void testPosition() {
Integer param = Integer.valueOf(123);
pojoObject.setPosition(param);
Object result = pojoObject.getPosition();
assertEquals(param, result);
}

}

