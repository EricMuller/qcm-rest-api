package com.emu.apps.qcm.domain.model.question;


import com.emu.apps.qcm.domain.model.account.Account;
import com.emu.apps.qcm.domain.model.base.DomainId;
import com.emu.apps.qcm.domain.model.category.MpttCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by eric on 05/06/2017.
 */


@Getter
@Setter
@NoArgsConstructor
public class Question extends DomainId <QuestionId> {

    private String type;

    private String questionText;

    private MpttCategory mpttCategory;

    private List <Response> responses;

    private List <QuestionTag> tags;

    private String status;

    private String tip;

    private Account owner;

}
