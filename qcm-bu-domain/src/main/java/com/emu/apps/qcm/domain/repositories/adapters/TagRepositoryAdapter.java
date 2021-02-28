package com.emu.apps.qcm.domain.repositories.adapters;


import com.emu.apps.qcm.domain.models.Tag;
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

    private final TagPersistencePort tagInfraService;

    private final QuestionPersistencePort questionPersistencePort;

    public TagRepositoryAdapter(TagPersistencePort tagInfraService, QuestionPersistencePort questionPersistencePort) {
        this.tagInfraService = tagInfraService;
        this.questionPersistencePort = questionPersistencePort;
    }

    @Override
    public Page <Tag> getTags(String search, Pageable pageable, String principal) throws IOException {

        var criterias = CriteriaUtils.toCriteria(search);
        Optional <String> firstLetter = CriteriaUtils.getAttribute("firstLetter", criterias);
        Optional <String> used = CriteriaUtils.getAttribute("used", criterias);

        return tagInfraService.findAllByPage(firstLetter, pageable, principal);
    }

    @Override
    public Tag getTagByUuid(String uuid) {
        return tagInfraService.findByUuid(uuid);
    }

    @Override
    public Tag saveTag(Tag tag) {
        return tagInfraService.save(tag);
    }

    public Tag findOrCreateByLibelle(String libelle, String principal) {
        return tagInfraService.findOrCreateByLibelle(libelle, principal);
    }


}
