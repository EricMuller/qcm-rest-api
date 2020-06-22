package com.emu.apps.qcm.infrastructure.ports;

import com.emu.apps.qcm.models.TagDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TagPersistencePort {


    TagDto save(TagDto tag);

    TagDto findById(Long id);

    TagDto findByUuid(String uuid);

    TagDto findOrCreateByLibelle(String libelle, String principal);

    Iterable <TagDto> findAll();

    Page <TagDto> findAllByPage(Optional <String> firstLetter, Pageable pageable, String principal);

}
