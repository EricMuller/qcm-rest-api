package com.emu.apps.qcm.spi.persistence.adapters.jpa.specifications;

import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags.TagEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.Locale;
import java.util.Objects;

public abstract class BaseSpecification<T> {

    private static final String WILDCARD = "%";

    protected static final String ID = "id";

    protected static final String UUID = "uuid";

    protected static final String LIBELLE = "libelle";

    protected static final String CREATED_BY = "createdBy";

    protected static final String PUBLISHED = "published";


    public abstract Specification <T> build() ;

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

    protected Specification <TagEntity> fieldStartWith(String attribute, String value) {
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
