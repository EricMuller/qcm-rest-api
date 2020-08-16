package com.emu.apps.qcm.spi.persistence.adapters.jpa.specifications;

import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.upload.UploadEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
public final class UploadSpecificationBuilder extends BaseSpecification<UploadEntity> {

    private String principal;

    public UploadSpecificationBuilder() {
        // nope
    }

    public UploadSpecificationBuilder setPrincipal(String principal) {
        this.principal = principal;
        return this;
    }

    @Override
    public Specification<UploadEntity> build() {
        Specification where = fieldEquals(CREATED_BY, principal);
        return (root, query, cb) -> where(where).toPredicate(root, query, cb);
    }


}
