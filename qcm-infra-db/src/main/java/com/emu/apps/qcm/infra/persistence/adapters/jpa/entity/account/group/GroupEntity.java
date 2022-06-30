package com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.account.group;


import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.account.AccountEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.common.IdentifiableEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor

@EntityListeners(AuditingEntityListener.class)
@Table(name = "group",
        indexes = {
                @Index(name = "IDX_GRP_CODE", columnList = "code"),
        }
        , uniqueConstraints = {@UniqueConstraint(name = "UK_GRP_OWNER_CODE", columnNames = {"owner_id","code"})}
)
public class GroupEntity extends IdentifiableEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CODE", nullable = false)
    private String code;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    private AccountEntity owner;

}
