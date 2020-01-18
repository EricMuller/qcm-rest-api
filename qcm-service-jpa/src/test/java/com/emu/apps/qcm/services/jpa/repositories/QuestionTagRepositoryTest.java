package com.emu.apps.qcm.services.jpa.repositories;

import com.emu.apps.qcm.services.SpringBootTestCase;
import com.emu.apps.qcm.services.jpa.entity.questions.Question;
import com.emu.apps.qcm.services.jpa.entity.tags.Tag;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class QuestionTagRepositoryTest extends SpringBootTestCase {

    @Autowired
    private QuestionTagRepository questionTagRepository;

    @Test
    public void findByQuestionId() {

        Question question = getFixtureService().createQuestionsAndGetFirst();

        Iterable<Tag> tags = questionTagRepository.findByQuestionId(question.getId());

        Assertions.assertThat(tags).isNotNull();
        Assertions.assertThat(tags).isNotEmpty();
        Assertions.assertThat(tags.spliterator().estimateSize()).isEqualTo(2);

    }

}
