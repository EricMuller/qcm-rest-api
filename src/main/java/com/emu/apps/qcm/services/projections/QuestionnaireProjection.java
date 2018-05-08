package com.emu.apps.qcm.services.projections;


import com.emu.apps.qcm.services.entity.epics.Epic;
import org.hibernate.annotations.Immutable;


import java.util.Date;

@Immutable
public interface QuestionnaireProjection {

    public Long getId();

    public Long getVersion();

    public Date getDateCreation();

    public String getTitle();

    public Epic getEpic();

    public String getDescription();

    public String getLocale();


}
