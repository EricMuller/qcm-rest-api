package com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.account.profile;

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

@Entity
@Table(name = "ACCOUNT_PROFIL")
@Getter
@Setter
@NoArgsConstructor
public class AccountProfileEntity {

    @EmbeddedId
    private AccountProfileId id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID", insertable = false, updatable = false)
    private AccountEntity account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROFILE_ID", insertable = false, updatable = false)
    private ProfileEntity profile;

}
