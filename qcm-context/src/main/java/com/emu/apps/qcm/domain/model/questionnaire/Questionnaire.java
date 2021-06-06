package com.emu.apps.qcm.domain.model.questionnaire;

import com.emu.apps.qcm.domain.model.Status;
import com.emu.apps.qcm.domain.model.category.Category;
import com.emu.apps.qcm.domain.model.base.DomainId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Questionnaire extends DomainId <QuestionnaireId> {

    private String title;

    private String description;

    private Category category;

    private Set <QuestionnaireTag> tags;

    private Status status;

    private String website;

    private Boolean published;



}
