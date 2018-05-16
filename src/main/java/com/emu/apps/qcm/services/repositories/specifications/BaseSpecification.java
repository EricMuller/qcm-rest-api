package com.emu.apps.qcm.services.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;

import java.security.Principal;

public abstract class BaseSpecification<T, U> {

    private final String wildcard = "%";

    public abstract Specification<T> getFilter(U request, Principal principal);

    protected String containsLowerCase(String searchField) {
        return wildcard + searchField.toLowerCase() + wildcard;
    }
}