package com.emu.apps.qcm.services.jpa.projections;


import com.emu.apps.qcm.services.jpa.entity.questions.Response;

import java.util.List;


public interface QuestionResponseProjection extends QuestionProjection {

    List <Response> getResponses();

}
