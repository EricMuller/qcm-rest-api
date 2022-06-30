package com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.account.profile;


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
@Table(name = "profil",
        indexes = {
                @Index(name = "IDX_PFL_CODE", columnList = "code"),
        }
        , uniqueConstraints = {@UniqueConstraint(name = "UK_PFL_OWNER_CODE", columnNames = {"owner_id","code"})}
)
public class ProfileEntity extends IdentifiableEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "code")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    private AccountEntity owner;
}
