package com.emu.apps.qcm.infra.persistence;

import com.emu.apps.qcm.domain.models.tag.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TagPersistencePort {


    Tag save(Tag tag);

    Tag findById(Long id);

    Tag findByUuid(String uuid);

    Tag findOrCreateByLibelle(String libelle, String principal);

    Iterable <Tag> findAll();

    Page <Tag> findAllByPage(Optional <String> firstLetter, Pageable pageable, String principal);

}
