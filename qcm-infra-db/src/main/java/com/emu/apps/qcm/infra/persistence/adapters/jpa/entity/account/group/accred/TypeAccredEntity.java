package com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.account.group.accred;


import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.common.IdentifiableEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Getter
@Setter
@NoArgsConstructor

@EntityListeners(AuditingEntityListener.class)
@Table(name = "type_accred"
)
public class TypeAccredEntity extends IdentifiableEntity<String> {

    @Id
    @Column(name = "ID",length = 20)
    private String id;

    @Column(name = "DESCRIPTION")
    private String description;

}
