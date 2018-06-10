package com.emu.apps.qcm.services.impl.jpa;

import com.emu.apps.qcm.services.TagService;
import com.emu.apps.qcm.services.entity.tags.Tag;
import com.emu.apps.qcm.services.repositories.TagRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * Created by eric on 14/06/2017.
 */
@Service
@Transactional
public class TagServiceImpl implements TagService {

    protected final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private TagRepository tagRepository;

    @Override
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag findById(Long id) {
        return tagRepository.findOne(id);
    }

    @Override
    public Tag findOrCreateByLibelle(String libelle) {
        Tag tag = tagRepository.findByLibelle(libelle);
        if (Objects.isNull(tag)) {
            tag = save(new Tag(libelle, true));
        }
        return tag;
    }

    @Override
    public Tag findByLibelle(String libelle) {
        return tagRepository.findByLibelle(libelle);
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
