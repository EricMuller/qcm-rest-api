package com.emu.apps.qcm.domain.model.questionnaire;

import com.emu.apps.qcm.domain.model.base.Aggregate;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
//@NoArgsConstructor
//@AllArgsConstructor
public class QuestionnaireAggregate extends Aggregate <Questionnaire> {
    private final List <QuestionnaireQuestion> questionnaireQuestions;

    public QuestionnaireAggregate(Questionnaire root, List <QuestionnaireQuestion> questionnaireQuestions) {
        super(root);
        this.questionnaireQuestions = questionnaireQuestions;
    }
}
