package com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

@SuppressWarnings("serial")
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableAccountEntity<U extends Serializable> extends IdentifiableEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CREATED_BY", updatable = false, nullable = false)
    // @CreatedBy
    protected U createdBy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MODIFIED_BY")
    // @LastModifiedBy
    private U lastModifiedBy;

    @SuppressWarnings("squid:S3437")
    @Column(name = "CREATED_DATE", nullable = false, updatable = false)
    @CreatedDate
    private ZonedDateTime dateCreation;

    @SuppressWarnings("squid:S3437")
    @Column(name = "MODIFIED_DATE")
    @LastModifiedDate
    private ZonedDateTime dateModification;

    protected AuditableAccountEntity() {
    }

    public AuditableAccountEntity(UUID uuid) {
        super(uuid);
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
