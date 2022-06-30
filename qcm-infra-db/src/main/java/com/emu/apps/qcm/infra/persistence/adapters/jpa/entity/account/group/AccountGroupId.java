package com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.account.group;

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
public class AccountGroupId implements Serializable {
    private static final long serialVersionUID = -184075071703258579L;

    @Column(name = "ACCOUNT_ID")
    private Long accountId;

    @Column(name = "GROUP_ID")
    private Long groupId;

    public AccountGroupId(Long accountId, Long groupId) {
        this.accountId = accountId;
        this.groupId = groupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountGroupId that = (AccountGroupId) o;
        return Objects.equals(accountId, that.accountId) &&
                Objects.equals(groupId, that.groupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, groupId);
    }
}
