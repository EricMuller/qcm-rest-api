package com.emu.apps.qcm.services.jpa.projections;

import afu.org.checkerframework.checker.igj.qual.Immutable;
import com.emu.apps.qcm.services.jpa.entity.questions.Response;

import java.util.List;

@Immutable
public interface QuestionResponseProjection extends QuestionProjection {

     List<Response> getResponses();

}
