package com.emu.apps.qcm.domain.repositories;

import com.emu.apps.qcm.domain.models.tag.Tag;
import com.emu.apps.qcm.domain.models.base.PrincipalId;
import com.emu.apps.qcm.domain.models.tag.TagId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface TagRepository {
    Page <Tag> getTags(String search, Pageable pageable, PrincipalId principal) throws IOException;

    Tag getTagById(TagId tagId);

    Tag saveTag(Tag tag);

    Tag findOrCreateByLibelle(String libelle, PrincipalId principal);
}
