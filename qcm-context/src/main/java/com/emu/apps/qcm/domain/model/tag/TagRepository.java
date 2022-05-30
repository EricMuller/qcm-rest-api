package com.emu.apps.qcm.domain.model.tag;

import com.emu.apps.qcm.domain.model.base.PrincipalId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface TagRepository {
    Page <Tag> getTags(String search, Pageable pageable, PrincipalId principal) throws IOException;

    Tag getTagOfId(TagId tagId);

    Tag saveTag(Tag tag);

    Tag findOrCreateByLibelle(String libelle, PrincipalId principal);
}
