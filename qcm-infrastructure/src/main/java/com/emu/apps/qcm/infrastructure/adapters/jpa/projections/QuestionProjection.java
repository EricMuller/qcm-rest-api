package com.emu.apps.qcm.infrastructure.adapters.jpa.projections;


import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.questions.TypeQuestion;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;

@Immutable
public interface QuestionProjection {

    Long getId();

    Long getVersion();

    LocalDateTime getDateCreation();

    Boolean getMandatory();

    Long getPosition();

    String getQuestion();

    TypeQuestion getType();

    String getUuid();
}
