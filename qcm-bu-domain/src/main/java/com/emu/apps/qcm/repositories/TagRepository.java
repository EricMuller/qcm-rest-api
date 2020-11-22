package com.emu.apps.qcm.repositories;

import com.emu.apps.qcm.aggregates.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface TagRepository {
    Page <Tag> getTags(String search, Pageable pageable, String principal) throws IOException;

    Tag getTagByUuid(String uuid);

    Tag saveTag(Tag tag);

    Tag findOrCreateByLibelle(String libelle, String principal);
}
