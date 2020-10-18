package com.emu.apps.qcm.spi.persistence.adapters.jpa.repositories;

import com.emu.apps.qcm.spi.infrastructure.DbFixture;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.config.SpringBootJpaTestConfig;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questionnaires.QuestionnaireEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.projections.QuestionResponseProjection;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest(classes = SpringBootJpaTestConfig.class)
@ActiveProfiles(value = "test")
public class QuestionnaireQuestionRepositoryTest {

    @Autowired
    private DbFixture dbFixture;

    @Autowired
    private QuestionnaireQuestionRepository questionnaireQuestionRepository;

    @Autowired
    private TagRepository tagRepository;


    @Test
    public void findQuestionsByQuestionnaireUuId() {

        dbFixture.emptyDatabase();

        QuestionnaireEntity questionnaire = dbFixture.createOneQuestionnaireWithTwoQuestionTags();

        Iterable <QuestionResponseProjection> questions = questionnaireQuestionRepository.findQuestionsByQuestionnaireUuiId(questionnaire.getUuid());
        Assertions.assertThat(questions).isNotEmpty();
        Assertions.assertThat(questions.spliterator().estimateSize()).isEqualTo(2);

        QuestionResponseProjection question = Iterables.getFirst(questions, null);
        Assertions.assertThat(question).isNotNull();
        Assertions.assertThat(question.getId()).isNotNull();
        Assertions.assertThat(question.getQuestion()).isNotNull().isEqualTo(DbFixture.QUESTION_QUESTION_1);
        Assertions.assertThat(question.getPosition()).isNotNull().isEqualTo(1L);
    }

    @Test
    public void findQuestionsByQuestionnaireIds() {

        dbFixture.emptyDatabase();

        QuestionnaireEntity questionnaire = dbFixture.createOneQuestionnaireWithTwoQuestionTags();

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
        Assertions.assertThat(question.getQuestion()).isNotNull().isEqualTo(DbFixture.QUESTION_QUESTION_1);
        Assertions.assertThat(question.getPosition()).isNotNull().isEqualTo(1L);
    }

}
