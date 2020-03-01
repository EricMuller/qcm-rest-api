package com.emu.apps.qcm.services.jpa.repositories;

import com.emu.apps.qcm.services.Fixture;
import com.emu.apps.qcm.services.config.SpringBootTestConfig;
import com.emu.apps.qcm.services.entity.questions.Question;
import com.emu.apps.qcm.services.entity.tags.Tag;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = SpringBootTestConfig.class)
@ActiveProfiles(value = "test")
public class QuestionTagRepositoryTest {

    @Autowired
    private Fixture fixture;

    @Autowired
    private QuestionTagRepository questionTagRepository;


    @Test
    public void findByQuestionId() {

        Question question = fixture.createQuestionsAndGetFirst();

        Iterable <Tag> tags = questionTagRepository.findByQuestionId(question.getId());

        Assertions.assertThat(tags).isNotNull();
        Assertions.assertThat(tags).isNotEmpty();
        Assertions.assertThat(tags.spliterator().estimateSize()).isEqualTo(2);

    }

}
