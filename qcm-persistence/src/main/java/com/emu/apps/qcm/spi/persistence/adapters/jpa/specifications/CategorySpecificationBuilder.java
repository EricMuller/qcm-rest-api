package com.emu.apps.qcm.spi.persistence.adapters.jpa.specifications;

import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.category.Type;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static org.springframework.data.jpa.domain.Specification.where;


@Component
@NoArgsConstructor
public final class CategorySpecificationBuilder<T> extends BaseSpecification <T> {

    private static String TYPE_FIELD_NAME = "type";

    private String principal;

    private Type type;

    public CategorySpecificationBuilder setPrincipal(String principal) {
        this.principal = principal;
        return this;
    }

    public CategorySpecificationBuilder setType(Type type) {
        this.type = type;
        return this;
    }

    protected Specification <T> fieldEquals(String attribute, Type value) {
        return (root, query, cb) -> Objects.isNull(value) ? null : cb.equal(root.get(attribute), value);
    }

    @Override
    public Specification <T> build() {
        return (root, query, cb) -> where(
                fieldEquals(TYPE_FIELD_NAME, type)).and(fieldEquals(CREATED_BY, principal)
        ).toPredicate(root, query, cb);
    }

}
