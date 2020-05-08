package com.emu.apps.qcm.business;


import com.emu.apps.qcm.services.TagService;
import com.emu.apps.qcm.services.jpa.specifications.TagSpecificationBuilder;
import com.emu.apps.qcm.web.dtos.TagDto;
import com.emu.apps.qcm.web.mappers.TagMapper;
import com.emu.apps.shared.parsers.rsql.CriteriaUtils;
import com.emu.apps.shared.security.PrincipalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

/**
 * Created by eric on 05/06/2017.
 */
@Service
public class TagDelegate {

    private final TagService tagService;

    private final TagMapper tagMapper;

    @Autowired
    public TagDelegate(TagService tagService, TagMapper tagMapper) {
        this.tagService = tagService;
        this.tagMapper = tagMapper;
    }

    public Page <TagDto> getTagsByPAge(String search, Pageable pageable, Principal principal) throws IOException {

        var criterias = CriteriaUtils.toCriteria(search);
        Optional <String> firstLetter = CriteriaUtils.getAttribute("firstLetter", criterias);

        var tagSpecificationBuilder = new TagSpecificationBuilder()
                .setPrincipal(PrincipalUtils.getEmail(principal))
                .setLetter(firstLetter.isPresent() ? firstLetter.get() : null);

        return tagMapper.pageToDto(tagService.findAllByPage(tagSpecificationBuilder.build(), pageable));
    }


    public TagDto getTagById(Long id) {
        return tagMapper.modelToDto(tagService.findById(id).orElse(null));
    }

    public TagDto saveTag(TagDto tagDto) {
        var tag = tagMapper.dtoToModel(tagDto);
        return tagMapper.modelToDto(tagService.save(tag));
    }

}
