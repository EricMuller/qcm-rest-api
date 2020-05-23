package com.emu.apps.qcm.services;


import com.emu.apps.qcm.domain.TagDOService;
import com.emu.apps.qcm.domain.jpa.specifications.TagSpecificationBuilder;
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
public class TagService {

    private final TagDOService tagDOService;

    private final TagMapper tagMapper;

    public TagService(TagDOService tagDOService, TagMapper tagMapper) {
        this.tagDOService = tagDOService;
        this.tagMapper = tagMapper;
    }

    public Page <TagDto> getTagsByPAge(String search, Pageable pageable, Principal principal) throws IOException {

        var criterias = CriteriaUtils.toCriteria(search);
        Optional <String> firstLetter = CriteriaUtils.getAttribute("firstLetter", criterias);

        var tagSpecificationBuilder = new TagSpecificationBuilder()
                .setPrincipal(PrincipalUtils.getEmail(principal))
                .setLetter(firstLetter.isPresent() ? firstLetter.get() : null);

        return tagMapper.pageToDto(tagDOService.findAllByPage(tagSpecificationBuilder.build(), pageable));
    }


    public TagDto getTagById(Long id) {
        return tagMapper.modelToDto(tagDOService.findById(id).orElse(null));
    }

    public TagDto saveTag(TagDto tagDto) {
        var tag = tagMapper.dtoToModel(tagDto);
        return tagMapper.modelToDto(tagDOService.save(tag));
    }

}
