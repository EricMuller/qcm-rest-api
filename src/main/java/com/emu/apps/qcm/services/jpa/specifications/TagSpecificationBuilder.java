package com.emu.apps.qcm.services.jpa.specifications;

import com.emu.apps.qcm.services.jpa.entity.tags.Tag;
import com.emu.apps.shared.parsers.rsql.Criteria;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import static org.springframework.data.jpa.domain.Specification.where;


@Component
public final class TagSpecificationBuilder extends BaseSpecification<Tag, Criteria[]> {

    private String letter;

    private String principal;

    public TagSpecificationBuilder() {
        //nope
    }

    public TagSpecificationBuilder setPrincipal(String principal) {
        this.principal = principal;
        return this;
    }

    public TagSpecificationBuilder setLetter(String letter) {
        this.letter = letter;
        return this;
    }

    public Specification<Tag> build() {
        return (root, query, cb) -> where(fieldStartWith(LIBELLE, letter))
                .and(fieldEquals(CREATED_BY, principal)
                ).toPredicate(root, query, cb);
    }

}
