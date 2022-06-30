package com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.account.group.accred;

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
public class GroupAccredId implements Serializable {

    @Column(name = "GROUP_ID")
    private Long groupId;

    @Column(name = "ACCRED_ID")
    private Long accredId;

    public GroupAccredId(Long groupId, Long accredId) {
        this.groupId = groupId;
        this.accredId = accredId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupAccredId that = (GroupAccredId) o;
        return Objects.equals(groupId, that.groupId) &&
                Objects.equals(accredId, that.accredId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, accredId);
    }
}
