package com.emu.apps.qcm.domain.ports;

import com.emu.apps.qcm.domain.dtos.TagDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.security.Principal;

public interface TagServicePort {
    Page <TagDto> getTagsByPAge(String search, Pageable pageable, Principal principal) throws IOException;

    TagDto getTagById(Long id);

    TagDto saveTag(TagDto tagDto);
}
