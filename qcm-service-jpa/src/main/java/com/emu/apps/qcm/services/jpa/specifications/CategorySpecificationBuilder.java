package com.emu.apps.qcm.services.jpa.specifications;

import com.emu.apps.qcm.services.entity.category.Category.Type;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static org.springframework.data.jpa.domain.Specification.where;


@Component
@NoArgsConstructor
public final class CategorySpecificationBuilder<T> extends BaseSpecification <T> {

    private static String TYPE = "type";

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

    @Override
    public Specification <T> build() {
        return (root, query, cb) -> where(
                fieldEquals(TYPE, Objects.nonNull(type) ? type.name() : null))
                .and(fieldEquals(CREATED_BY, principal)
                ).toPredicate(root, query, cb);
    }

}
