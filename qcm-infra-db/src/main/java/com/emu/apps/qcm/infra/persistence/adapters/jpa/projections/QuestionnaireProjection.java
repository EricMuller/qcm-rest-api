package com.emu.apps.qcm.infra.persistence.adapters.jpa.projections;


import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.mptt.MpttCategoryEntity;
import org.hibernate.annotations.Immutable;

import java.util.Date;
import java.util.UUID;

@Immutable
public interface QuestionnaireProjection {

    MpttCategoryEntity getCategory();

    Date getDateCreation();

    Date getDateModification();

    String getLocale();

    UUID getUuid();

    String getStatus();

    String getTitle();

    Long getVersion();

}
