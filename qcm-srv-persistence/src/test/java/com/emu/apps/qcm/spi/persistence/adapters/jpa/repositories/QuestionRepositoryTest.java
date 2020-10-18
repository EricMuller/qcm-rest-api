package com.emu.apps.qcm.spi.persistence.adapters.jpa.repositories;

import com.emu.apps.qcm.spi.infrastructure.DbFixture;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.config.SpringBootJpaTestConfig;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questions.QuestionEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questions.ResponseEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags.QuestionTagEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags.TagEntity;
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

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static com.emu.apps.qcm.spi.infrastructure.DbFixture.QUESTION_TAG_LIBELLE_1;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SpringBootJpaTestConfig.class)
@ActiveProfiles(value = "test")
class QuestionRepositoryTest {

    @Autowired
    private DbFixture dbFixture;

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    @Transactional
     void findOne() {
        QuestionEntity question = dbFixture.createQuestionsAndGetFirst();
        assertNotNull(question.getId());
        Optional <QuestionEntity> newQuestion = questionRepository.findById(question.getId());
        assertNotNull(newQuestion.orElse(null));
        assertNotNull(newQuestion.orElse(null).getId());
        assertEquals(DbFixture.QUESTION_QUESTION_1, newQuestion.get().getQuestionText());
        // Assert.assertEquals(RESPONSE, newQuestion.getResponse());
    }

    @Test
    @DisplayName("Test LazyInitializationException with lazy collection Tags")
     void findOneLazyInitializationException() {


        QuestionEntity question = dbFixture.createQuestionsAndGetFirst();
        assertNotNull(question.getId());
        Optional <QuestionEntity> newQuestion = questionRepository.findById(question.getId());

        Assertions.assertThat(newQuestion).isPresent();

        assertThrows(LazyInitializationException.class, () -> newQuestion.get().getQuestionTags().size());

    }

    @Test
    @DisplayName("Test LazyInitializationException with lazy collection responses")
     void findByIdAndFetchTagsLazyInitializationException() {
        QuestionEntity question = dbFixture.createQuestionsAndGetFirst();

        QuestionEntity newQuestion = questionRepository.findByIdAndFetchTags(question.getId());

        assertThrows(LazyInitializationException.class, () -> newQuestion.getResponses().size());
    }


    @Test
     void findByIdAndFetchTags() {
        QuestionEntity question = dbFixture.createQuestionsAndGetFirst();

        QuestionEntity newQuestion = questionRepository.findByIdAndFetchTags(question.getId());

        Assertions.assertThat(newQuestion).isNotNull();
        Assertions.assertThat(newQuestion.getQuestionTags()).isNotEmpty();
        Assertions.assertThat(newQuestion.getQuestionTags().size()).isEqualTo(2);

        Optional <QuestionTagEntity> optional = newQuestion.getQuestionTags()
                .stream()
                .filter(q -> QUESTION_TAG_LIBELLE_1.equals(q.getTag().getLibelle()))
                .findFirst();

        Assertions.assertThat(optional).isPresent();

    }

    @Test
     void findByIdAndFetchTagsAndResponses() {
        QuestionEntity question = dbFixture.createQuestionsAndGetFirst();

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

        Assertions.assertThat(optional).isPresent();

        // responses
        Assertions.assertThat(newQuestion.getResponses()).isNotEmpty();
        Assertions.assertThat(newQuestion.getQuestionTags().size()).isEqualTo(2);

        ResponseEntity response = Iterables.getFirst(newQuestion.getResponses(), null);

        Assertions.assertThat(response).isNotNull();

    }

    @Test
     void findAllQuestionsTags() {

        dbFixture.emptyDatabase();

        dbFixture.createOneQuestionnaireWithTwoQuestionTags();

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

        Assertions.assertThat(questionTag).isNotNull();
        Assertions.assertThat(questionTag.getTag()).isNotNull();

    }


    @Test
     void findAllQuestions()  {

        dbFixture.emptyDatabase();

        dbFixture.createOneQuestionnaireWithTwoQuestionTags();

        TagEntity tag1 = dbFixture.findTagbyLibelle(DbFixture.QUESTION_TAG_LIBELLE_1, () -> SpringBootJpaTestConfig.USER_TEST);
        Assertions.assertThat(tag1).isNotNull();

        Specification <QuestionEntity> specification = new QuestionSpecificationBuilder(PrincipalUtils.getEmailOrName( (Principal) () -> SpringBootJpaTestConfig.USER_TEST))
                .setTagUuids(new UUID[]{tag1.getUuid()})
                .build();

        Pageable pageable = PageRequest.of(0, 3, Sort.by("id"));

        Page <QuestionEntity> page = questionRepository.findAll(specification, pageable);
        Assertions.assertThat(page).isNotNull();

        Assertions.assertThat(page.getNumberOfElements()).isEqualTo(2);
    }


}
