package com.emu.apps.qcm.services.jpa;

import com.emu.apps.qcm.services.TagService;
import com.emu.apps.qcm.services.jpa.entity.tags.Tag;
import com.emu.apps.qcm.services.jpa.repositories.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by eric on 14/06/2017.
 */
@Service
@Transactional
public class TagServiceImpl implements TagService {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }


    @Override
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return tagRepository.findById(id);
    }


    @Override
    public Tag findOrCreateByLibelle(String libelle, Principal principal) {
        Tag tag = tagRepository.findByLibelle(libelle, principal.getName());
        if (Objects.isNull(tag)) {
            tag = save(new Tag(libelle, true));
        }
        return tag;
    }


    public Tag findByLibelle(String libelle, Principal principal) {
        return tagRepository.findByLibelle(libelle, principal.getName());
    }

    @Override
    public Iterable<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public Page<Tag> findAllByPage(Specification<Tag> specifications, Pageable pageable) {
        return tagRepository.findAll(specifications, pageable);
    }

    @Override
    public Iterable<Tag> findByLibelleContaining(String libelle) {
        return tagRepository.findByLibelleContaining(libelle);
    }


}
