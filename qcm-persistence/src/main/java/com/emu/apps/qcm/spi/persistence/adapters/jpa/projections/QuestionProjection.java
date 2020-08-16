package com.emu.apps.qcm.spi.persistence.adapters.jpa.projections;


import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questions.TypeQuestionEnum;
import org.hibernate.annotations.Immutable;

import java.time.ZonedDateTime;

@Immutable
public interface QuestionProjection {

    Long getId();

    Long getVersion();

    ZonedDateTime getDateCreation();

    Boolean getMandatory();

    Long getPosition();

    String getQuestion();

    TypeQuestionEnum getType();

    String getUuid();
}
