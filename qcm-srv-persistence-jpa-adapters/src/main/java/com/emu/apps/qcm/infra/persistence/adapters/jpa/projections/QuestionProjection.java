package com.emu.apps.qcm.infra.persistence.adapters.jpa.projections;


import com.emu.apps.qcm.domain.models.question.TypeQuestion;
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

    TypeQuestion getType();

    String getUuid();
}
