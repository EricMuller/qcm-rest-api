package com.emu.apps.qcm.spi.persistence.adapters.jpa.repositories;

import com.emu.apps.qcm.spi.infrastructure.DbFixture;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.config.SpringBootJpaTestConfig;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questions.QuestionEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags.TagEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = SpringBootJpaTestConfig.class)
@ActiveProfiles(value = "test")
public class QuestionTagRepositoryTest {

    @Autowired
    private DbFixture dbFixture;

    @Autowired
    private QuestionTagRepository questionTagRepository;


    @Test
    public void findByQuestionId() {

        QuestionEntity question = dbFixture.createQuestionsAndGetFirst();

        Iterable <TagEntity> tags = questionTagRepository.findByQuestionId(question.getId());

        Assertions.assertThat(tags).isNotNull();
        Assertions.assertThat(tags).isNotEmpty();
        Assertions.assertThat(tags.spliterator().estimateSize()).isEqualTo(2);

    }

}
