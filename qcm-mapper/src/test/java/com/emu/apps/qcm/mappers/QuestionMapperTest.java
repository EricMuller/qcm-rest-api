package com.emu.apps.qcm.mappers;

import com.emu.apps.qcm.domain.entity.questions.Question;

import com.emu.apps.qcm.web.dtos.CategoryDto;
import com.emu.apps.qcm.web.dtos.QuestionDto;
import com.emu.apps.qcm.web.dtos.QuestionTagDto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {QuestionMapperImpl.class, QuestionTagMapperImpl.class, ResponseMapperImpl.class, CategoryMapperImpl.class})
public class QuestionMapperTest {

    @Autowired
    private QuestionMapper questionMapper;

    @Test
    public void test() {

        QuestionDto questionDto = new QuestionDto();
        questionDto.setId(1L);
        questionDto.setQuestion("Question");

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(1L);
        questionDto.setCategory(categoryDto);

        QuestionTagDto questionTagDto = new QuestionTagDto();
        questionTagDto.setId(1L);

        questionTagDto.setLibelle("Test");

        Question question = questionMapper.dtoToModel(questionDto);

        Assertions.assertThat(question).isNotNull();

    }
}
