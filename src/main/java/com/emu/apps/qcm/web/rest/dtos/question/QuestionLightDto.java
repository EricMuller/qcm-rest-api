package com.emu.apps.qcm.web.rest.dtos.question;


import com.emu.apps.qcm.services.entity.questions.Type;
import com.emu.apps.qcm.web.rest.dtos.EntityDto;
import io.swagger.annotations.ApiModel;

/**
 * Created by eric on 05/06/2017.
 */
@ApiModel(value = "Question")
public class QuestionLightDto extends EntityDto {

    private String question;

    private Type type;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

}
