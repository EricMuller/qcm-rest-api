package com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.account.profile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class AccountProfileId implements Serializable {
    private static final long serialVersionUID = -184075071703258579L;

    @Column(name = "ACCOUNT_ID")
    private Long accountId;

    @Column(name = "PROFILE_ID")
    private Long profileId;

    public AccountProfileId(Long accountId, Long profileId) {
        this.accountId = accountId;
        this.profileId = profileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountProfileId that = (AccountProfileId) o;
        return Objects.equals(accountId, that.accountId) &&
                Objects.equals(profileId, that.profileId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, profileId);
    }
}
