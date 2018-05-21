package com.emu.apps.qcm.web.mappers;

import com.emu.apps.qcm.services.entity.questions.Question;
import com.emu.apps.qcm.web.rest.dtos.QuestionDto;
import com.emu.apps.qcm.web.rest.dtos.QuestionTagDto;
import com.emu.apps.qcm.web.rest.mappers.QuestionMapper;
import com.emu.apps.qcm.web.rest.mappers.QuestionMapperImpl;
import com.emu.apps.qcm.web.rest.mappers.QuestionTagMapperImpl;
import com.emu.apps.qcm.web.rest.mappers.ResponseMapperImpl;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {QuestionMapperImpl.class, QuestionTagMapperImpl.class, ResponseMapperImpl.class})
public class QuestionMapperTest {

    @Autowired
    private QuestionMapper questionMapper;

    @Test
    public void test() {

        QuestionDto questionDto = new QuestionDto();
        questionDto.setId(1L);
        questionDto.setQuestion("Question");

        QuestionTagDto questionTagDto = new QuestionTagDto();
        questionTagDto.setId(1L);

        questionTagDto.setLibelle("Test");


        Question question = questionMapper.dtoToModel(questionDto);


        Assertions.assertThat(question).isNotNull();

    }
}
