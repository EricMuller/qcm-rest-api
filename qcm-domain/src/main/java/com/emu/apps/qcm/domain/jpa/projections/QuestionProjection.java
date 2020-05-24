package com.emu.apps.qcm.domain.jpa.projections;


import com.emu.apps.qcm.domain.entity.questions.TypeQuestion;
import org.hibernate.annotations.Immutable;

import java.util.Date;

@Immutable
public interface QuestionProjection {

    Long getId();

    Long getVersion();

    Date getDateCreation();

    Boolean getMandatory();

    Long getPosition();

    String getQuestion();

    TypeQuestion getType();

    String getUuid();
}
