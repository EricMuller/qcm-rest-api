package com.emu.apps.qcm.domain.model.tag;


import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.infra.persistence.QuestionPersistencePort;
import com.emu.apps.qcm.infra.persistence.TagPersistencePort;
import com.emu.apps.shared.parsers.rsql.CriteriaUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

/**
 * Tag Business Delegate
 * <p>
 *
 * @author eric
 * @since 2.2.0
 */
@Service
class TagRepositoryAdapter implements TagRepository {

    private final TagPersistencePort tagPersistencePort;


    public TagRepositoryAdapter(TagPersistencePort tagInfraService) {
        this.tagPersistencePort = tagInfraService;

    }

    @Override
    public Page <Tag> getTags(String search, Pageable pageable, PrincipalId principal) throws IOException {

        var criterias = CriteriaUtils.toCriteria(search);
        Optional <String> firstLetter = CriteriaUtils.getAttribute("firstLetter", criterias);


        return tagPersistencePort.findAllByPage(firstLetter, pageable, principal.toUuid());
    }

    @Override
    public Tag getTagOfId(TagId tagId) {
        return tagPersistencePort.findByUuid(tagId.toUuid());
    }

    @Override
    public Tag saveTag(Tag tag) {
        return tagPersistencePort.save(tag);
    }

    public Tag findOrCreateByLibelle(String libelle, PrincipalId principal) {
        return tagPersistencePort.findOrCreateByLibelle(libelle, principal.toUuid());
    }


}
