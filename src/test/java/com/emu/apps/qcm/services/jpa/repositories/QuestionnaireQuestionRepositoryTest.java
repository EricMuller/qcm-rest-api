package com.emu.apps.qcm.services.jpa.repositories;

import com.emu.apps.qcm.services.FixtureService;
import com.emu.apps.qcm.services.jpa.entity.questionnaires.Questionnaire;
import com.emu.apps.qcm.services.jpa.projections.QuestionResponseProjection;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles(value = "test")
public class QuestionnaireQuestionRepositoryTest {

    private static final String SPLIT = "######################################################################";

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private FixtureService questionnaireFixture;

    @Autowired
    private QuestionnaireQuestionRepository questionnaireQuestionRepository;

    @Autowired
    private TagRepository tagRepository;


    @Test
    public void findQuestionsByQuestionnaireId() {

        Questionnaire questionnaire = questionnaireFixture.createQuestionQuestionnaireTag();
        logger.error(SPLIT);

        Iterable<QuestionResponseProjection> questions = questionnaireQuestionRepository.findQuestionsByQuestionnaireId(questionnaire.getId());
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

        Questionnaire questionnaire = questionnaireFixture.createQuestionQuestionnaireTag();
        logger.error(SPLIT);

        List<Long> longListTag = Lists.newArrayList();
        tagRepository.findAll().forEach( (tag) -> longListTag.add(tag.getId()));

        Assertions.assertThat(longListTag).isNotEmpty();
        List<Long> longList = Lists.newArrayList(questionnaire.getId());

        Page<QuestionResponseProjection> questions = questionnaireQuestionRepository.findQuestionsByQuestionnaireIdsAndTagIds(longList, longListTag, null);
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