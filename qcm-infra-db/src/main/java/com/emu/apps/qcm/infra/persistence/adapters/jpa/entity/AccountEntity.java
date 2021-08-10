package com.emu.apps.qcm.infra.persistence.adapters.jpa.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.Objects;
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
public class AccountEntity implements Serializable {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NonNull
    private String email;

    private String userName;

    private String firstName;

    private String lastName;

    private String company;

    private String apiKey;

    @Version
    @Column(name = "VERSION")
    private Long version;

    public AccountEntity(UUID uuid) {
        this.id = uuid;
    }

    @PrePersist
    public void autofill() {
        if (Objects.isNull(id)) {
            this.id = UUID.randomUUID();
        }
    }


}
