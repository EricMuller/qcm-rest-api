package com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.account.group.accred;

import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.account.group.GroupEntity;
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
@Table(name = "GROUP_ACCRED")
@Getter
@Setter
@NoArgsConstructor
public class GroupAccredEntity implements Serializable {

    @EmbeddedId
    private GroupAccredId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_ID", insertable = false, updatable = false)
    private GroupEntity group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCRED_ID", insertable = false, updatable = false)
    private AccredEntity accred;

}
