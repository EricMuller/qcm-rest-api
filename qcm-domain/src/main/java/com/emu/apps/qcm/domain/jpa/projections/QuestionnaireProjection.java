package com.emu.apps.qcm.domain.jpa.projections;


import com.emu.apps.qcm.domain.entity.Status;
import com.emu.apps.qcm.domain.entity.category.Category;
import org.hibernate.annotations.Immutable;

import java.util.Date;

@Immutable
public interface QuestionnaireProjection {

    Long getId();

    Long getVersion();

    Date getDateCreation();

    String getTitle();

    Category getCategory();

    Status getStatus();

    String getDescription();

    String getLocale();


}
