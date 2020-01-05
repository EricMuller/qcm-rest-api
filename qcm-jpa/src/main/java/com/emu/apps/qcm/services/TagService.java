package com.emu.apps.qcm.services;

import com.emu.apps.qcm.services.jpa.entity.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.security.Principal;
import java.util.Optional;

public interface TagService {


    Tag save(Tag tag);

    Optional<Tag> findById(Long id);

    Tag  findOrCreateByLibelle(String libelle, Principal principal);

    Iterable<Tag> findAll();

    Page<Tag> findAllByPage(Specification<Tag> specifications, Pageable pageable);

    Iterable<Tag> findByLibelleContaining(String libelle);
}
