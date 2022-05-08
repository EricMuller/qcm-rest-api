package com.emu.apps.qcm.domain.model.questionnaire;


import com.emu.apps.qcm.domain.model.category.MpttCategory;
import com.emu.apps.qcm.domain.model.base.DomainId;
import com.emu.apps.qcm.domain.model.question.QuestionId;
import com.emu.apps.qcm.domain.model.question.QuestionTag;
import com.emu.apps.qcm.domain.model.question.Response;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

/**
 * Created by eric on 05/06/2017.
 */

@Getter
@Setter
@NoArgsConstructor
public class QuestionnaireQuestion extends DomainId<QuestionId> {

    private String question;

    private String type;

    private MpttCategory mpttCategory;

    private List <Response> responses;

    private Set <QuestionTag> questionTags;

    private String tip;

    private String status;

    private Integer position;

    private Integer points;

}
