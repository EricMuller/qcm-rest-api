package com.emu.apps.qcm.infrastructure.adapters.jpa.projections;


import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.Status;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.category.Category;
import org.hibernate.annotations.Immutable;

import java.util.Date;
import java.util.UUID;

@Immutable
public interface QuestionnaireProjection {

    Category getCategory();

    Date getDateCreation();

    Date getDateModification();

    String getLocale();

    UUID getUuid();

    Status getStatus();

    String getTitle();

    Long getVersion();

}
