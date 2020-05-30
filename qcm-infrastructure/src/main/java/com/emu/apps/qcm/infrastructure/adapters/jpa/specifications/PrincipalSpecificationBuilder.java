package com.emu.apps.qcm.infrastructure.adapters.jpa.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import static org.springframework.data.jpa.domain.Specification.where;


@Component
public final class PrincipalSpecificationBuilder<T> extends BaseSpecification <T> {

    private String principal;

    public PrincipalSpecificationBuilder() {
        //nope
    }

    public PrincipalSpecificationBuilder setPrincipal(String principal) {
        this.principal = principal;
        return this;
    }


    @Override
    public Specification <T> build() {
        return (root, query, cb) -> where(fieldEquals(CREATED_BY, principal)
        ).toPredicate(root, query, cb);
    }

}
