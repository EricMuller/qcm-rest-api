package com.emu.apps.qcm.infrastructure.adapters.jpa.entity.questionnaires;

import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.questions.Question;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionnaireQuestionTest {
private QuestionnaireQuestion pojoObject;
public QuestionnaireQuestionTest() throws Exception {
this.pojoObject = new QuestionnaireQuestion();
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
Questionnaire param = new Questionnaire();
pojoObject.setQuestionnaire(param);
Object result = pojoObject.getQuestionnaire();
assertEquals(param, result);
}

@Test
public void testQuestion() {
Question param = new Question();
pojoObject.setQuestion(param);
Object result = pojoObject.getQuestion();
assertEquals(param, result);
}

@Test
public void testPosition() {
Long param = Long.valueOf(123);
pojoObject.setPosition(param);
Object result = pojoObject.getPosition();
assertEquals(param, result);
}

}

