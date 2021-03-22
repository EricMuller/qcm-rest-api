package com.emu.apps.qcm.domain.repositories.adapters;


import com.emu.apps.qcm.domain.models.tag.Tag;
import com.emu.apps.qcm.domain.models.base.PrincipalId;
import com.emu.apps.qcm.domain.models.tag.TagId;
import com.emu.apps.qcm.domain.repositories.TagRepository;
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
public class TagRepositoryAdapter implements TagRepository {

    private final TagPersistencePort tagPersistencePort;

    private final QuestionPersistencePort questionPersistencePort;

    public TagRepositoryAdapter(TagPersistencePort tagInfraService, QuestionPersistencePort questionPersistencePort) {
        this.tagPersistencePort = tagInfraService;
        this.questionPersistencePort = questionPersistencePort;
    }

    @Override
    public Page <Tag> getTags(String search, Pageable pageable, PrincipalId principal) throws IOException {

        var criterias = CriteriaUtils.toCriteria(search);
        Optional <String> firstLetter = CriteriaUtils.getAttribute("firstLetter", criterias);
        Optional <String> used = CriteriaUtils.getAttribute("used", criterias);

        return tagPersistencePort.findAllByPage(firstLetter, pageable, principal.toUUID());
    }

    @Override
    public Tag getTagById(TagId tagId) {
        return tagPersistencePort.findByUuid(tagId.toUUID());
    }

    @Override
    public Tag saveTag(Tag tag) {
        return tagPersistencePort.save(tag);
    }

    public Tag findOrCreateByLibelle(String libelle, PrincipalId principal) {
        return tagPersistencePort.findOrCreateByLibelle(libelle, principal.toUUID());
    }


}
