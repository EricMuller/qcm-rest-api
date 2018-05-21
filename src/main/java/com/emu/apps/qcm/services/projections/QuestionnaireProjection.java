package com.emu.apps.qcm.services.projections;


import com.emu.apps.qcm.services.entity.epics.Category;
import org.hibernate.annotations.Immutable;

import java.util.Date;

@Immutable
public interface QuestionnaireProjection {

    Long getId();

    Long getVersion();

    Date getDateCreation();

    String getTitle();

    Category getEpic();

    String getDescription();

    String getLocale();


}
