package com.emu.apps.qcm.services.projections;

import com.emu.apps.qcm.services.entity.questions.Response;
import jdk.nashorn.internal.ir.annotations.Immutable;

import java.util.List;

@Immutable
public interface QuestionResponseProjection extends QuestionProjection {



    public List<Response> getResponses();


}
