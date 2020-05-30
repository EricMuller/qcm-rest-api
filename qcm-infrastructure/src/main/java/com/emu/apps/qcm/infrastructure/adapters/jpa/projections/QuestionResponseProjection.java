package com.emu.apps.qcm.infrastructure.adapters.jpa.projections;


import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.questions.Response;

import java.util.List;


public interface QuestionResponseProjection extends QuestionProjection {

    List <Response> getResponses();

}
