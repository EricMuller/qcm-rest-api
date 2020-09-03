package com.emu.apps.qcm.spi.persistence.adapters.jpa.repositories;

import com.emu.apps.qcm.spi.infrastructure.DbFixture;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.config.SpringBootTestConfig;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questions.QuestionEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questions.ResponseEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags.QuestionTagEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags.Tag;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.specifications.QuestionSpecificationBuilder;
import com.emu.apps.shared.security.PrincipalUtils;
import com.google.common.collect.Iterables;
import org.assertj.core.api.Assertions;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static com.emu.apps.qcm.spi.infrastructure.DbFixture.QUESTION_TAG_LIBELLE_1;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SpringBootTestConfig.class)
@ActiveProfiles(value = "test")
public class QuestionRepositoryTest {

    @Autowired
    private DbFixture fixture;

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    @Transactional
    public void findOne() {
        QuestionEntity question = fixture.createQuestionsAndGetFirst();
        assertNotNull(question.getId());
        Optional <QuestionEntity> newQuestion = questionRepository.findById(question.getId());
        assertNotNull(newQuestion.orElse(null));
        assertNotNull(newQuestion.orElse(null).getId());
        assertEquals(DbFixture.QUESTION_QUESTION_1, newQuestion.get().getLibelle());
        // Assert.assertEquals(RESPONSE, newQuestion.getResponse());
    }

    @Test
    @DisplayName("Test LazyInitializationException with lazy collection Tags")
    public void findOneLazyInitializationException() {


        QuestionEntity question = fixture.createQuestionsAndGetFirst();
        assertNotNull(question.getId());
        Optional <QuestionEntity> newQuestion = questionRepository.findById(question.getId());

        Assertions.assertThat(newQuestion.orElse(null)).isNotNull();

        assertThrows(LazyInitializationException.class, () -> newQuestion.get().getQuestionTags().size());

    }

    @Test
    @DisplayName("Test LazyInitializationException with lazy collection responses")
    public void findByIdAndFetchTagsLazyInitializationException() {
        QuestionEntity question = fixture.createQuestionsAndGetFirst();

        QuestionEntity newQuestion = questionRepository.findByIdAndFetchTags(question.getId());

        assertThrows(LazyInitializationException.class, () -> newQuestion.getResponses().size());
    }


    @Test
    public void findByIdAndFetchTags() {
        QuestionEntity question = fixture.createQuestionsAndGetFirst();

        QuestionEntity newQuestion = questionRepository.findByIdAndFetchTags(question.getId());

        Assertions.assertThat(newQuestion).isNotNull();
        Assertions.assertThat(newQuestion.getQuestionTags()).isNotEmpty();
        Assertions.assertThat(newQuestion.getQuestionTags().size()).isEqualTo(2);

        Optional <QuestionTagEntity> optional = newQuestion.getQuestionTags()
                .stream()
                .filter(q -> QUESTION_TAG_LIBELLE_1.equals(q.getTag().getLibelle()))
                .findFirst();

        Assertions.assertThat(optional.isPresent()).isTrue();

    }

    @Test
    public void findByIdAndFetchTagsAndResponses() {
        QuestionEntity question = fixture.createQuestionsAndGetFirst();

        //test create
        QuestionEntity newQuestion = questionRepository.findByIdAndFetchTagsAndResponses(question.getId());

        Assertions.assertThat(newQuestion).isNotNull();

        //tags
        Assertions.assertThat(newQuestion.getQuestionTags()).isNotEmpty();
        Assertions.assertThat(newQuestion.getQuestionTags().size()).isEqualTo(2);

        Optional <QuestionTagEntity> optional = newQuestion.getQuestionTags()
                .stream()
                .filter(q -> QUESTION_TAG_LIBELLE_1.equals(q.getTag().getLibelle()))
                .findFirst();

        Assertions.assertThat(optional.isPresent()).isTrue();

        // responses
        Assertions.assertThat(newQuestion.getResponses()).isNotEmpty();
        Assertions.assertThat(newQuestion.getQuestionTags().size()).isEqualTo(2);

        ResponseEntity response = Iterables.getFirst(newQuestion.getResponses(), null);

        Assertions.assertThat(response).isNotNull();

    }

    @Test
    public void findAllQuestionsTags() {

        fixture.emptyDatabase();

        fixture.createOneQuestionnaireWithTwoQuestionTags();

        Page <QuestionEntity> page = questionRepository.findAllQuestionsTags(null);

        Assertions.assertThat(page).isNotNull();

        List <QuestionEntity> content = page.getContent();
        Assertions.assertThat(content).isNotEmpty();

        QuestionEntity first = Iterables.getFirst(content, null);

        Assertions.assertThat(first).isNotNull();

        Set <QuestionTagEntity> questionTags = first.getQuestionTags();
        Assertions.assertThat(questionTags).isNotEmpty();
        Assertions.assertThat(questionTags.size()).isEqualTo(2);

        QuestionTagEntity questionTag = Iterables.getFirst(questionTags, null);

        Assertions.assertThat(questionTag.getTag()).isNotNull();

    }


    @Test
    public void findAllQuestions()  {


        fixture.emptyDatabase();

        fixture.createOneQuestionnaireWithTwoQuestionTags();

        Tag tag1 = fixture.findTagbyLibelle(fixture.QUESTION_TAG_LIBELLE_1, () -> SpringBootTestConfig.USER_TEST);
        Assertions.assertThat(tag1).isNotNull();

        Specification <QuestionEntity> specification = new QuestionSpecificationBuilder(PrincipalUtils.getEmail(() -> SpringBootTestConfig.USER_TEST))
                .setTagUuids(new UUID[]{tag1.getUuid()})
                .build();

        Pageable pageable = PageRequest.of(0, 3, Sort.by("id"));

        Page <QuestionEntity> page = questionRepository.findAll(specification, pageable);
        Assertions.assertThat(page).isNotNull();

        Assertions.assertThat(page.getNumberOfElements()).isEqualTo(2);
    }


}
