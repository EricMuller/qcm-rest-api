package com.emu.apps.qcm.domain.model.question;


import com.emu.apps.qcm.domain.model.category.Category;
import com.emu.apps.qcm.domain.model.base.DomainId;
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
public class Question extends DomainId {

    private String type;

    private String questionText;

    private Category category;

    private List <Response> responses;

    private Set <QuestionTag> questionTags;

    private String status;

    private String tip;

}
