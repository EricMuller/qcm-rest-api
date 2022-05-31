package com.emu.apps.qcm.domain.model.question;


import com.emu.apps.qcm.domain.model.account.Account;
import com.emu.apps.qcm.domain.model.base.DomainId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by eric on 05/06/2017.
 */
@Getter
@Setter
@NoArgsConstructor
public class QuestionLight extends DomainId<QuestionId> {

    private String questionText;

    private String type;

    private String status;

    private Account owner;

    private int numeroVersion;
}
