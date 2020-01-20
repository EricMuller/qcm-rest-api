package com.emu.apps.qcm.services.jpa.repositories;

import com.emu.apps.qcm.services.FixtureService;
import com.emu.apps.qcm.services.SpringBootTestCase;
import com.emu.apps.qcm.services.entity.questionnaires.Questionnaire;
import com.emu.apps.qcm.services.jpa.projections.QuestionResponseProjection;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;

public class QuestionnaireQuestionRepositoryTest extends SpringBootTestCase {

    @Autowired
    private QuestionnaireQuestionRepository questionnaireQuestionRepository;

    @Autowired
    private TagRepository tagRepository;

    @Test
    public void findQuestionsByQuestionnaireId() {

        Questionnaire questionnaire = getFixtureService().createOneQuestionnaireWithTwoQuestionTags();

        Iterable <QuestionResponseProjection> questions = questionnaireQuestionRepository.findQuestionsByQuestionnaireId(questionnaire.getId());
        Assertions.assertThat(questions).isNotEmpty();
        Assertions.assertThat(questions.spliterator().estimateSize()).isEqualTo(2);

        QuestionResponseProjection question = Iterables.getFirst(questions, null);
        Assertions.assertThat(question).isNotNull();
        Assertions.assertThat(question.getId()).isNotNull();
        Assertions.assertThat(question.getQuestion()).isNotNull().isEqualTo(FixtureService.QUESTION_QUESTION_1);
        Assertions.assertThat(question.getPosition()).isNotNull().isEqualTo(1L);
    }

    @Test
    public void findQuestionsByQuestionnaireIds() {

        Questionnaire questionnaire = getFixtureService().createOneQuestionnaireWithTwoQuestionTags();

        List <Long> longListTag = Lists.newArrayList();
        tagRepository.findAll().forEach((tag) -> longListTag.add(tag.getId()));

        Assertions.assertThat(longListTag).isNotEmpty();
        List <Long> longList = Lists.newArrayList(questionnaire.getId());

        Page <QuestionResponseProjection> questions = questionnaireQuestionRepository.findQuestionsByQuestionnaireIdsAndTagIds(longList, longListTag, null);
        Assertions.assertThat(questions).isNotNull();
        Assertions.assertThat(questions.getContent()).isNotEmpty();
        Assertions.assertThat(questions.getContent().spliterator().estimateSize()).isEqualTo(2);

        QuestionResponseProjection question = Iterables.getFirst(questions.getContent(), null);
        Assertions.assertThat(question).isNotNull();
        Assertions.assertThat(question.getId()).isNotNull();
        Assertions.assertThat(question.getQuestion()).isNotNull().isEqualTo(FixtureService.QUESTION_QUESTION_1);
        Assertions.assertThat(question.getPosition()).isNotNull().isEqualTo(1L);
    }

}
