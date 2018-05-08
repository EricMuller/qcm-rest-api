package com.emu.apps.qcm.services.projections;


import com.emu.apps.qcm.services.entity.epics.Epic;
import org.hibernate.annotations.Immutable;

import java.util.Date;

@Immutable
public interface QuestionnaireProjection {

    Long getId();

    Long getVersion();

    Date getDateCreation();

    String getTitle();

    Epic getEpic();

    String getDescription();

    String getLocale();


}
