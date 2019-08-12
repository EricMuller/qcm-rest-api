package com.emu.apps.qcm.services.jpa.repositories;

import com.emu.apps.qcm.services.FixtureTest;
import com.emu.apps.qcm.services.jpa.entity.questions.Question;
import com.emu.apps.qcm.services.jpa.entity.tags.Tag;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(value = "test")
public class QuestionTagRepositoryTest extends FixtureTest {

    @Autowired
    private QuestionTagRepository questionTagRepository;

    @Test
    public void findByQuestionId() {

        Question question = fixtureService.createQuestionsAndGetFirst();

        Iterable<Tag> tags = questionTagRepository.findByQuestionId(question.getId());

        Assertions.assertThat(tags).isNotNull();
        Assertions.assertThat(tags).isNotEmpty();
        Assertions.assertThat(tags.spliterator().estimateSize()).isEqualTo(2);

    }

}
