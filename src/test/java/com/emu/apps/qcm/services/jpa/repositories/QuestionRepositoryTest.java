package com.emu.apps.qcm.services.jpa.repositories;

import com.emu.apps.qcm.services.FixtureService;
import com.emu.apps.qcm.services.SpringBootTestCase;
import com.emu.apps.qcm.services.jpa.entity.questions.Question;
import com.emu.apps.qcm.services.jpa.entity.questions.Response;
import com.emu.apps.qcm.services.jpa.entity.tags.QuestionTag;
import com.emu.apps.qcm.services.jpa.entity.tags.Tag;
import com.emu.apps.qcm.services.jpa.specifications.QuestionSpecificationBuilder;
import com.google.common.collect.Iterables;
import org.assertj.core.api.Assertions;
import org.hibernate.LazyInitializationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class QuestionRepositoryTest extends SpringBootTestCase {

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    @Transactional
    public void findOne() {
        Question question = getFixtureService().createQuestionsAndGetFirst();
        Assert.assertNotNull(question.getId());
        Optional<Question> newQuestion = questionRepository.findById(question.getId());
        Assert.assertNotNull(newQuestion.orElse(null));
        Assert.assertNotNull(newQuestion.orElse(null).getId());
        Assert.assertEquals(FixtureService.QUESTION_QUESTION_1, newQuestion.get().getQuestion());
        //Assert.assertEquals(RESPONSE, newQuestion.getResponse());
    }


    @Test(expected = LazyInitializationException.class)
    public void findOneLazyInitializationException() {
        Question question = getFixtureService().createQuestionsAndGetFirst();
        Assert.assertNotNull(question.getId());
        Optional<Question> newQuestion = questionRepository.findById(question.getId());

        Assertions.assertThat(newQuestion.orElse(null)).isNotNull();

        Assertions.assertThat(newQuestion.get().getQuestionTags()).isNotEmpty();


    }

    @Test
    public void findByIdAndFetchTags() {
        Question question = getFixtureService().createQuestionsAndGetFirst();

        Question newQuestion = questionRepository.findByIdAndFetchTags(question.getId());

        Assertions.assertThat(newQuestion).isNotNull();
        Assertions.assertThat(newQuestion.getQuestionTags()).isNotEmpty();
        Assertions.assertThat(newQuestion.getQuestionTags().size()).isEqualTo(2);

        Optional<QuestionTag> optional = newQuestion.getQuestionTags().stream().filter(q -> FixtureService.QUESTION_TAG_LIBELLE_1.equals(q.getTag().getLibelle())).findFirst();
        Assertions.assertThat(optional.isPresent()).isTrue();

    }

    @Test(expected = LazyInitializationException.class)
    public void findByIdAndFetchTagsLazyInitializationException() {
        Question question = getFixtureService().createQuestionsAndGetFirst();

        Question newQuestion = questionRepository.findByIdAndFetchTags(question.getId());

        Assertions.assertThat(newQuestion.getResponses()).isNotEmpty();
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

        Optional<QuestionTag> optional = newQuestion.getQuestionTags().stream().filter(q -> FixtureService.QUESTION_TAG_LIBELLE_1.equals(q.getTag().getLibelle())).findFirst();
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

        Page<Question> page = questionRepository.findAllQuestionsTags(null);

        Assertions.assertThat(page).isNotNull();

        List<Question> content = page.getContent();
        Assertions.assertThat(content).isNotEmpty();

        Question first = Iterables.getFirst(content, null);

        Assertions.assertThat(first).isNotNull();

        Set<QuestionTag> questionTags = first.getQuestionTags();
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

        Specification<Question> specification = new QuestionSpecificationBuilder()
                .setTagIds(new Long[]{tag1.getId()})
                .setPrincipal(getPrincipal().getName()).build();

        Pageable pageable = PageRequest.of(0, 3, Sort.by("id"));

        Page<Question> page = questionRepository.findAll(specification, pageable);
        Assertions.assertThat(page).isNotNull();

        Assertions.assertThat(page.getNumberOfElements()).isEqualTo(2);
    }


}
