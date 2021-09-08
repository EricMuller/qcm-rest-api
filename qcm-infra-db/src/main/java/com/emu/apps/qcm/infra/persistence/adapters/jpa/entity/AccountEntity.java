package com.emu.apps.qcm.infra.persistence.adapters.jpa.entity;

import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.common.IdentifiableEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

import static java.util.Objects.isNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "account",
        indexes = {@Index(name = "IDX_USR_EMAIL_IDX", columnList = "email")})
@Cacheable
@EntityListeners(AuditingEntityListener.class)
public class AccountEntity extends IdentifiableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_generator")
    @SequenceGenerator(name = "account_generator", sequenceName = "account_seq", allocationSize = 1)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @NonNull
    @Column(name = "EMAIL")
    private String email;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "COMPAGNY")
    private String company;

    @Column(name = "API_KEY")
    private String apiKey;

    @Column(name = "CREATED_BY", updatable = false)
    protected String createdBy;

    @Column(name = "MODIFIED_BY")
    @LastModifiedBy
    private String lastModifiedBy;

    @SuppressWarnings("squid:S3437")
    @Column(name = "CREATED_DATE", nullable = false, updatable = false)
    @CreatedDate
    private ZonedDateTime dateCreation;

    @SuppressWarnings("squid:S3437")
    @Column(name = "MODIFIED_DATE")
    @LastModifiedDate
    private ZonedDateTime dateModification;

    public AccountEntity(UUID uuid) {
        super(uuid);
    }

    @Override
    public void prePersist() {
        if (Objects.nonNull(uuid)) {
            this.createdBy = this.uuid.toString();
        }
    }
}
