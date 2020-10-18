package com.emu.apps.qcm.spi.persistence.adapters.jpa.entity;

import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.common.AuditableEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "account",
        indexes = {@Index(name = "IDX_USR_EMAIL_IDX", columnList = "email")})
@Cacheable
public class UserEntity extends AuditableEntity <String> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    @SequenceGenerator(name = "user_generator", sequenceName = "user_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    private String email;

    private String userName;

    private String firstName;

    private String lastName;

    private String company;

    private String apiKey;

}
