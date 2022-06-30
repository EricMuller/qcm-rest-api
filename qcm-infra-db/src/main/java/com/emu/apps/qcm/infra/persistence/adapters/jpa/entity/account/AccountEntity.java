package com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.account;

import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.account.group.AccountGroupEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.account.profile.AccountProfileEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.common.IdentifiableEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

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
public class AccountEntity extends IdentifiableEntity <Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 20)
    private Set <AccountGroupEntity> groups = new HashSet <>();

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 20)
    private Set <AccountProfileEntity> profiles = new HashSet <>();

//    @ManyToMany
//    @JoinTable(
//            name = "ACCOUNT_GROUP",
//            joinColumns = @JoinColumn(name = "ACCOUNT_ID"),
//            inverseJoinColumns = @JoinColumn(name = "GROUP_ID"))
//    private Set<GroupEntity> groupEntities;

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
