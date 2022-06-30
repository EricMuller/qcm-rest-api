package com.emu.apps.qcm.application;

import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.category.MpttCategory;
import com.emu.apps.qcm.domain.model.tag.Tag;
import com.emu.apps.qcm.domain.model.tag.TagId;
import com.emu.apps.qcm.infra.persistence.MpptCategoryPersistencePort;
import com.emu.apps.qcm.infra.persistence.TagQuestionPersistencePort;
import org.springframework.stereotype.Service;

@Service
public class RefCatalog {


    private final TagQuestionPersistencePort tagQuestionPersistencePort;

    private final MpptCategoryPersistencePort mpptCategoryPersistencePort;


    public RefCatalog(TagQuestionPersistencePort tagQuestionPersistencePort, MpptCategoryPersistencePort mpptCategoryPersistencePort) {
        this.tagQuestionPersistencePort = tagQuestionPersistencePort;
        this.mpptCategoryPersistencePort = mpptCategoryPersistencePort;
    }

    public Tag getTagQuestionOfId(TagId tagId) {
        return tagQuestionPersistencePort.findByUuid(tagId.toUuid());
    }

    public Iterable <MpttCategory> getCategories(PrincipalId principal, String type) {
        return mpptCategoryPersistencePort.findCategories(principal.toUuid(), type);
    }

}
