package com.emu.apps.qcm.spi.persistence.adapters.jpa.projections;


import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questions.ResponseEntity;

import java.util.List;


public interface QuestionResponseProjection extends QuestionProjection {

    List <ResponseEntity> getResponses();

}
