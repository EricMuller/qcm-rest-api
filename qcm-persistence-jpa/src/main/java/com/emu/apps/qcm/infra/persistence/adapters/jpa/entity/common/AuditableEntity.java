package com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.common;

import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.tags.TagEntity;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

@SuppressWarnings("serial")
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntity<U extends Serializable> implements Serializable {

    @Column(name = "uuid", nullable = false, updatable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    @NaturalId
    private UUID uuid = UUID.randomUUID();

    @Column(name = "created_by", updatable = false)
    @CreatedBy
    protected U createdBy;

    @Column(name = "modified_by")
    @LastModifiedBy
    private U lastModifiedBy;

    @SuppressWarnings("squid:S3437")
    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    private ZonedDateTime dateCreation;

    @SuppressWarnings("squid:S3437")
    @Column(name = "modified_date")
    @LastModifiedDate
    private ZonedDateTime dateModification;

    @Version
    private Long version;

    public abstract Long getId();

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @PrePersist
    public void prePersist() {
        this.setUuid(UUID.randomUUID());
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public ZonedDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(ZonedDateTime aDateCreation) {
        this.dateCreation = aDateCreation;
    }

    public ZonedDateTime getDateModification() {
        return dateModification;
    }

    public void setDateModification(ZonedDateTime dateModification) {
        this.dateModification = dateModification;
    }

    public U getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(U createdBy) {
        this.createdBy = createdBy;
    }

    public U getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(U lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public static abstract class BaseSpecification<T> {

        private static final String WILDCARD = "%";

        protected static final String ID = "id";

        protected static final String UUID = "uuid";

        protected static final String LIBELLE = "libelle";

        protected static final String CREATED_BY = "createdBy";

        protected static final String PUBLISHED = "published";


        public abstract Specification <T> build();

        protected Specification <T> fieldEquals(String attribute, String value) {
            return (root, query, cb) -> StringUtils.isEmpty(value) ? null : cb.equal(root.get(attribute), value);
        }

        protected Specification <T> fieldEquals(String attribute, Boolean value) {
            return (root, query, cb) -> Objects.isNull(value) ? null : cb.equal(root.get(attribute), value);
        }

        protected Specification <T> fieldContains(String attribute, String value) {
            return (root, query, cb) -> {
                if (StringUtils.isEmpty(value)) {
                    return null;
                }
                return cb.like(cb.lower(root.get(attribute)), toLikeLowerCase(value)
                );
            };
        }

        protected Specification <T> fieldStartWith(String attribute, String value) {
            return (root, query, cb) -> {
                if (StringUtils.isEmpty(value)) {
                    return null;
                }
                return cb.like(cb.lower(root.get(attribute)), value.toLowerCase(Locale.getDefault()) + '%');
            };
        }

        protected String toLikeLowerCase(String searchField) {
            return WILDCARD + searchField.toLowerCase(Locale.getDefault()) + WILDCARD;
        }

    }
}
