package com.emu.apps.qcm.services.jpa.projections;


import com.emu.apps.qcm.services.entity.Status;
import com.emu.apps.qcm.services.entity.category.QuestionCategory;
import com.emu.apps.qcm.services.entity.category.QuestionnaireCategory;
import org.hibernate.annotations.Immutable;

import java.util.Date;

@Immutable
public interface QuestionnaireProjection {

    Long getId();

    Long getVersion();

    Date getDateCreation();

    String getTitle();

    QuestionnaireCategory getCategory();

    Status getStatus();

    String getDescription();

    String getLocale();


}
