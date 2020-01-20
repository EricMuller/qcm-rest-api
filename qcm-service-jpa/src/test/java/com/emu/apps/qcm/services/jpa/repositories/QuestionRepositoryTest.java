package com.emu.apps.qcm.services.jpa.repositories;

import com.emu.apps.qcm.services.FixtureService;
import com.emu.apps.qcm.services.SpringBootTestCase;
import com.emu.apps.qcm.services.entity.questions.Question;
import com.emu.apps.qcm.services.entity.questions.Response;
import com.emu.apps.qcm.services.entity.tags.QuestionTag;
import com.emu.apps.qcm.services.entity.tags.Tag;
import com.emu.apps.qcm.services.jpa.specifications.QuestionSpecificationBuilder;
import com.emu.apps.shared.security.PrincipalUtils;
import com.google.common.collect.Iterables;
import org.assertj.core.api.Assertions;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionRepositoryTest extends SpringBootTestCase {

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    @Transactional
    public void findOne() {
        Question question = getFixtureService().createQuestionsAndGetFirst();
        assertNotNull(question.getId());
        Optional <Question> newQuestion = questionRepository.findById(question.getId());
        assertNotNull(newQuestion.orElse(null));
        assertNotNull(newQuestion.orElse(null).getId());
        assertEquals(FixtureService.QUESTION_QUESTION_1, newQuestion.get().getQuestion());
        //Assert.assertEquals(RESPONSE, newQuestion.getResponse());
    }


    @Test
    @DisplayName("Test LazyInitializationException with lazy collection Tags")
    public void findOneLazyInitializationException() {


        Question question = getFixtureService().createQuestionsAndGetFirst();
        assertNotNull(question.getId());
        Optional <Question> newQuestion = questionRepository.findById(question.getId());

        Assertions.assertThat(newQuestion.orElse(null)).isNotNull();

        assertThrows(LazyInitializationException.class, () -> newQuestion.get().getQuestionTags().size());


    }

    @Test
    @DisplayName("Test LazyInitializationException with lazy collection responses")
    public void findByIdAndFetchTagsLazyInitializationException() {
        Question question = getFixtureService().createQuestionsAndGetFirst();

        Question newQuestion = questionRepository.findByIdAndFetchTags(question.getId());

        assertThrows(LazyInitializationException.class, () -> newQuestion.getResponses().size());
    }


    @Test
    public void findByIdAndFetchTags() {
        Question question = getFixtureService().createQuestionsAndGetFirst();

        Question newQuestion = questionRepository.findByIdAndFetchTags(question.getId());

        Assertions.assertThat(newQuestion).isNotNull();
        Assertions.assertThat(newQuestion.getQuestionTags()).isNotEmpty();
        Assertions.assertThat(newQuestion.getQuestionTags().size()).isEqualTo(2);

        Optional <QuestionTag> optional = newQuestion.getQuestionTags().stream().filter(q -> FixtureService.QUESTION_TAG_LIBELLE_1.equals(q.getTag().getLibelle())).findFirst();
        Assertions.assertThat(optional.isPresent()).isTrue();

    }

    @Test
    public void findByIdAndFetchTagsAndResponses() {
        Question question = getFixtureService().createQuestionsAndGetFirst();

        //test create
        Question newQuestion = questionRepository.findByIdAndFetchTagsAndResponses(question.getId());

        Assertions.assertThat(newQuestion).isNotNull();

        //tags
        Assertions.assertThat(newQuestion.getQuestionTags()).isNotEmpty();
        Assertions.assertThat(newQuestion.getQuestionTags().size()).isEqualTo(2);

        Optional <QuestionTag> optional = newQuestion.getQuestionTags().stream().filter(q -> FixtureService.QUESTION_TAG_LIBELLE_1.equals(q.getTag().getLibelle())).findFirst();
        Assertions.assertThat(optional.isPresent()).isTrue();

        // responses
        Assertions.assertThat(newQuestion.getResponses()).isNotEmpty();
        Assertions.assertThat(newQuestion.getQuestionTags().size()).isEqualTo(2);

        Response response = Iterables.getFirst(newQuestion.getResponses(), null);

        Assertions.assertThat(response).isNotNull();

    }

    @Test
    public void findAllQuestionsTags() {

        getFixtureService().createOneQuestionnaireWithTwoQuestionTags();

        Page <Question> page = questionRepository.findAllQuestionsTags(null);

        Assertions.assertThat(page).isNotNull();

        List <Question> content = page.getContent();
        Assertions.assertThat(content).isNotEmpty();

        Question first = Iterables.getFirst(content, null);

        Assertions.assertThat(first).isNotNull();

        Set <QuestionTag> questionTags = first.getQuestionTags();
        Assertions.assertThat(questionTags).isNotEmpty();
        Assertions.assertThat(questionTags.size()).isEqualTo(2);

        QuestionTag questionTag = Iterables.getFirst(questionTags, null);

        Assertions.assertThat(questionTag.getTag()).isNotNull();

    }


    @Test
    public void findAllQuestions() {


        getFixtureService().createOneQuestionnaireWithTwoQuestionTags();

        Tag tag1 = getFixtureService().findTagbyLibelle(getFixtureService().QUESTION_TAG_LIBELLE_1, getPrincipal());
        Assertions.assertThat(tag1).isNotNull();

        Specification <Question> specification = new QuestionSpecificationBuilder()
                .setTagIds(new Long[]{tag1.getId()})
                .setPrincipal(PrincipalUtils.getEmail(getPrincipal())).build();

        Pageable pageable = PageRequest.of(0, 3, Sort.by("id"));

        Page <Question> page = questionRepository.findAll(specification, pageable);
        Assertions.assertThat(page).isNotNull();

        Assertions.assertThat(page.getNumberOfElements()).isEqualTo(2);
    }


}
