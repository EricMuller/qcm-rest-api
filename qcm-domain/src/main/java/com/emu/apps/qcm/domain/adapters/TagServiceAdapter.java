package com.emu.apps.qcm.domain.adapters;


import com.emu.apps.qcm.domain.ports.TagService;
import com.emu.apps.qcm.infrastructure.ports.TagDOService;
import com.emu.apps.qcm.infrastructure.adapters.jpa.specifications.TagSpecificationBuilder;
import com.emu.apps.qcm.web.dtos.TagDto;
import com.emu.apps.qcm.mappers.TagMapper;
import com.emu.apps.shared.parsers.rsql.CriteriaUtils;
import com.emu.apps.shared.security.PrincipalUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

/**
 *
 * Tag Business Delegate
 *<p>
 *
 * @since 2.2.0
 * @author eric
 */
@Service
public class TagServiceAdapter implements TagService {

    private final TagDOService tagDOService;

    private final TagMapper tagMapper;

    public TagServiceAdapter(TagDOService tagDOService, TagMapper tagMapper) {
        this.tagDOService = tagDOService;
        this.tagMapper = tagMapper;
    }

    @Override
    public Page <TagDto> getTagsByPAge(String search, Pageable pageable, Principal principal) throws IOException {

        var criterias = CriteriaUtils.toCriteria(search);
        Optional <String> firstLetter = CriteriaUtils.getAttribute("firstLetter", criterias);

        var tagSpecificationBuilder = new TagSpecificationBuilder()
                .setPrincipal(PrincipalUtils.getEmail(principal))
                .setLetter(firstLetter.isPresent() ? firstLetter.get() : null);

        return tagMapper.pageToDto(tagDOService.findAllByPage(tagSpecificationBuilder.build(), pageable));
    }


    @Override
    public TagDto getTagById(Long id) {
        return tagMapper.modelToDto(tagDOService.findById(id).orElse(null));
    }

    @Override
    public TagDto saveTag(TagDto tagDto) {
        var tag = tagMapper.dtoToModel(tagDto);
        return tagMapper.modelToDto(tagDOService.save(tag));
    }

}
