package com.emu.apps.qcm.domain.adapters;


import com.emu.apps.qcm.domain.ports.TagServicePort;
import com.emu.apps.qcm.infrastructure.ports.TagPersistencePort;
import com.emu.apps.qcm.domain.dtos.TagDto;
import com.emu.apps.shared.parsers.rsql.CriteriaUtils;
import com.emu.apps.shared.security.PrincipalUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

/**
 * Tag Business Delegate
 * <p>
 *
 * @author eric
 * @since 2.2.0
 */
@Service
public class TagServiceAdapter implements TagServicePort {

    private final TagPersistencePort tagInfraService;

    public TagServiceAdapter(TagPersistencePort tagInfraService) {
        this.tagInfraService = tagInfraService;
    }

    @Override
    public Page <TagDto> getTagsByPAge(String search, Pageable pageable, Principal principal) throws IOException {

        var criterias = CriteriaUtils.toCriteria(search);
        Optional <String> firstLetter = CriteriaUtils.getAttribute("firstLetter", criterias);

        return tagInfraService.findAllByPage(firstLetter,pageable,PrincipalUtils.getEmail(principal));
    }

    @Override
    public TagDto getTagById(Long id) {
        return tagInfraService.findById(id);
    }

    @Override
    public TagDto saveTag(TagDto tagDto) {
        return tagInfraService.save(tagDto);
    }

}
