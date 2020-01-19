package com.emu.apps.qcm.webmvc.services.jpa.specifications;

import com.emu.apps.qcm.webmvc.services.jpa.entity.tags.Tag;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import static org.springframework.data.jpa.domain.Specification.where;


@Component
public final class TagSpecificationBuilder extends BaseSpecification<Tag> {

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

    @Override
    public Specification<Tag> build() {
        return (root, query, cb) -> where(fieldStartWith(LIBELLE, letter))
                .and(fieldEquals(CREATED_BY, principal)
                ).toPredicate(root, query, cb);
    }

}
