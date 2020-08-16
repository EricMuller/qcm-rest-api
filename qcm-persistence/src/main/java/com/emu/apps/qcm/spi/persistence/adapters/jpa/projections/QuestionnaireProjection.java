package com.emu.apps.qcm.spi.persistence.adapters.jpa.projections;


import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.Status;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.category.CategoryEntity;
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

    Status getStatus();

    String getTitle();

    Long getVersion();

}
