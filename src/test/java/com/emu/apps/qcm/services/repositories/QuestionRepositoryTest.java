package com.emu.apps.qcm.services.repositories;

import com.emu.apps.qcm.services.FixtureService;
import com.emu.apps.qcm.services.entity.questions.Question;
import com.emu.apps.qcm.services.entity.tags.QuestionTag;
import com.emu.apps.qcm.services.entity.questions.Response;
import com.google.common.collect.Iterables;
import org.assertj.core.api.Assertions;
import org.hibernate.LazyInitializationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private FixtureService fixture;

    @Test
    @Transactional
    public void findOne() {
        Question question = fixture.createQuestion();
        Assert.assertNotNull(question.getId());
        Question newQuestion = questionRepository.findOne(question.getId());
        Assert.assertNotNull(question.getId());
        Assert.assertEquals(FixtureService.QUESTION_QUESTION_1, newQuestion.getQuestion());
        //Assert.assertEquals(RESPONSE, newQuestion.getResponse());
    }


    @Test(expected = LazyInitializationException.class)
    public void findOneLazyInitializationException() {
        Question question = fixture.createQuestion();
        Assert.assertNotNull(question.getId());
        Question newQuestion = questionRepository.findOne(question.getId());

        Assertions.assertThat(newQuestion.getQuestionTags()).isNotEmpty();
    }

    @Test
    public void findByIdAndFetchTags() {
        Question question = fixture.createQuestion();

        Question newQuestion = questionRepository.findByIdAndFetchTags(question.getId());

        Assertions.assertThat(newQuestion).isNotNull();
        Assertions.assertThat(newQuestion.getQuestionTags()).isNotEmpty();
        Assertions.assertThat(newQuestion.getQuestionTags().size()).isEqualTo(2);

        Optional<QuestionTag> optional = newQuestion.getQuestionTags().stream().filter(q -> FixtureService.TAG_LIBELLE_1.equals(q.getTag().getLibelle())).findFirst();
        Assertions.assertThat(optional.isPresent()).isTrue();

    }

    @Test(expected = LazyInitializationException.class)
    public void findByIdAndFetchTagsLazyInitializationException() {
        Question question = fixture.createQuestion();

        Question newQuestion = questionRepository.findByIdAndFetchTags(question.getId());

        Assertions.assertThat(newQuestion.getResponses()).isNotEmpty();
    }

    @Test
    public void findByIdAndFetchTagsAndResponses() {
        Question question = fixture.createQuestion();

        //test create
        Question newQuestion = questionRepository.findByIdAndFetchTagsAndResponses(question.getId());

        Assertions.assertThat(newQuestion).isNotNull();

        //tags
        Assertions.assertThat(newQuestion.getQuestionTags()).isNotEmpty();
        Assertions.assertThat(newQuestion.getQuestionTags().size()).isEqualTo(2);

        Optional<QuestionTag> optional = newQuestion.getQuestionTags().stream().filter(q -> FixtureService.TAG_LIBELLE_1.equals(q.getTag().getLibelle())).findFirst();
        Assertions.assertThat(optional.isPresent()).isTrue();

        // responses
        Assertions.assertThat(newQuestion.getResponses()).isNotEmpty();
        Assertions.assertThat(newQuestion.getQuestionTags().size()).isEqualTo(2);

        Response response = Iterables.getFirst(newQuestion.getResponses(), null);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getChoices()).isNotEmpty();
        Assertions.assertThat(response.getChoices().size()).isEqualTo(2);


    }

    @Test
    public void findAllQuestionsTags() {

        fixture.createQuestionQuestionnaireTag();

        Page<Question> page = questionRepository.findAllQuestionsTags(null);

        Assertions.assertThat(page).isNotNull();

        List<Question> content = page.getContent();
        Assertions.assertThat(content).isNotEmpty();

        Question first = Iterables.getFirst(content, null);

        Assertions.assertThat(first).isNotNull();
        //Assertions.assertThat(first.getQuestion()).isNotNull().isEqualTo(fixture.QUESTION_QUESTION_);

        Set<QuestionTag> questionTags = first.getQuestionTags();
        Assertions.assertThat(questionTags).isNotEmpty();
        Assertions.assertThat(questionTags.size()).isEqualTo(2);

        QuestionTag questionTag = Iterables.getFirst(questionTags, null);

        Assertions.assertThat(questionTag.getTag()).isNotNull();

    }


}