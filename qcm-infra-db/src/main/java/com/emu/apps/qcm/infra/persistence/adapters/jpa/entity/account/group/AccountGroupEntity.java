package com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.account.group;

import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.account.AccountEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "ACCOUNT_GROUP")
@Getter
@Setter
@NoArgsConstructor
public class AccountGroupEntity implements Serializable  {

    @EmbeddedId
    private AccountGroupId id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID", insertable = false, updatable = false)
    private AccountEntity account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_ID", insertable = false, updatable = false)
    private GroupEntity group;

}
