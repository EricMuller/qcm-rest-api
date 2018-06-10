package com.emu.apps.qcm.services;

import com.emu.apps.qcm.services.entity.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface TagService {

    Tag save(Tag tag);

    Tag findById(Long id);

    Tag findOrCreateByLibelle(String libelle);

    Tag findByLibelle(String libelle);

    Iterable<Tag> findAll();

    Page<Tag> findAllByPage(Specification<Tag> specifications, Pageable pageable);

    Iterable<Tag> findByLibelleContaining(String libelle);
}
