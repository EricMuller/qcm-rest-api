package com.emu.apps.qcm.services.jpa.projections;

import com.emu.apps.qcm.services.jpa.entity.questions.Response;
import jdk.nashorn.internal.ir.annotations.Immutable;

import java.util.List;

@Immutable
public interface QuestionResponseProjection extends QuestionProjection {

     List<Response> getResponses();

}
