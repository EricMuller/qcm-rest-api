package com.emu.apps.qcm.domain.jpa.projections;


import com.emu.apps.qcm.domain.entity.questions.Response;

import java.util.List;


public interface QuestionResponseProjection extends QuestionProjection {

    List <Response> getResponses();

}
