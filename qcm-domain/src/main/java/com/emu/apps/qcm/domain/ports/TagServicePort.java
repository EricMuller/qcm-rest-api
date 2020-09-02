package com.emu.apps.qcm.domain.ports;

import com.emu.apps.qcm.api.models.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface TagServicePort {
    Page <Tag> getTags(String search, Pageable pageable, String principal) throws IOException;

    Tag getTagByUuid(String uuid);

    Tag saveTag(Tag tagDto);
}
