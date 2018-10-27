package com.emu.apps.qcm.services.jpa.repositories.specifications.tags;

import com.emu.apps.qcm.services.jpa.entity.tags.Tag;
import com.emu.apps.qcm.services.jpa.repositories.specifications.BaseSpecification;
import com.emu.apps.qcm.web.rest.dtos.FilterDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Arrays;
import java.util.Optional;

import static org.springframework.data.jpa.domain.Specifications.where;

@Component
public class TagSpecification extends BaseSpecification<Tag, FilterDto[]> {

    private static final String FIRST_LETTER = "firstLetter";
    private static final String LIBELLE = "libelle";
    private static final String CREATED_BY = "createdBy";

    private Optional<String> getLetter(FilterDto[] filterDtos) {

        return Arrays.stream(filterDtos).
                filter((filterDto -> FIRST_LETTER.equals(filterDto.getName())))
                .map((filter) -> filter.getValue())
                .findFirst();

    }

    @Override
    public Specification<Tag> getSpecifications(final FilterDto[] filters, Principal principal) {
        return (root, query, cb) -> {
            return where(tagLibelleStartBy(getLetter(filters)))
                    .and(tagCreatdByEqual(principal.getName())
                    ).toPredicate(root, query, cb);
        };
    }

    private Specification<Tag> tagLibelleContains(String title) {
        return tagAttributeContains(LIBELLE, title);
    }

    private Specification<Tag> tagCreatdByEqual(String name) {
        return tagAttributeEquals(CREATED_BY, name);
    }

    private Specification<Tag> tagLibelleStartBy(Optional<String> letter) {
        return tagAttributeStartWith(LIBELLE, letter);
    }


    private Specification<Tag> tagAttributeContains(String attribute, String value) {
        return (root, query, cb) -> {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            return cb.like(
                    cb.lower(root.get(attribute)),
                    containsLowerCase(value)
            );
        };
    }

    private Specification<Tag> tagAttributeStartWith(String attribute, Optional<String> value) {
        return (root, query, cb) -> {
            if (!value.isPresent()) {
                return null;
            }
            return cb.like(cb.lower(root.get(attribute)), value.get().toLowerCase() + '%');
        };
    }

    private Specification<Tag> tagAttributeEquals(String attribute, String value) {
        return (root, query, cb) -> {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            return cb.equal(root.get(attribute), value);
        };
    }

}