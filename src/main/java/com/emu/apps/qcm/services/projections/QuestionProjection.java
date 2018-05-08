package com.emu.apps.qcm.services.projections;


import com.emu.apps.qcm.services.entity.questions.Type;
import org.hibernate.annotations.Immutable;

import java.util.Date;

@Immutable
public interface QuestionProjection {

//    @Value("#target.id")
    public Long getId();

    public Long getVersion();

    public Date getDateCreation();

    public Boolean getMandatory();

    public Long getPosition();

    public String getQuestion();

    public Type getType();

    public String getUuid();
}
