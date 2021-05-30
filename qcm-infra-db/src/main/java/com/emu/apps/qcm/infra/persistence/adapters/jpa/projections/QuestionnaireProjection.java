package com.emu.apps.qcm.infra.persistence.adapters.jpa.projections;


import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.category.CategoryEntity;
import org.hibernate.annotations.Immutable;

import java.util.Date;
import java.util.UUID;

@Immutable
public interface QuestionnaireProjection {

    CategoryEntity getCategory();

    Date getDateCreation();

    Date getDateModification();

    String getLocale();

    UUID getUuid();

    String getStatus();

    String getTitle();

    Long getVersion();

}
