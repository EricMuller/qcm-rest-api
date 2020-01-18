package com.emu.apps.qcm.services.jpa.projections;


import com.emu.apps.qcm.services.jpa.entity.questions.Type;
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

    Type getType();

    String getUuid();
}
