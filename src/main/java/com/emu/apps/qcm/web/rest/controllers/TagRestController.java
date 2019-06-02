package com.emu.apps.qcm.web.rest.controllers;


import com.emu.apps.qcm.services.TagService;
import com.emu.apps.qcm.services.jpa.entity.tags.Tag;
import com.emu.apps.qcm.services.jpa.repositories.specifications.tags.TagSpecification;
import com.emu.apps.qcm.web.rest.TagRestApi;
import com.emu.apps.qcm.web.rest.dtos.FilterDto;
import com.emu.apps.qcm.web.rest.dtos.SuggestDto;
import com.emu.apps.qcm.web.rest.dtos.TagDto;
import com.emu.apps.qcm.web.rest.mappers.TagMapper;
import com.emu.apps.qcm.web.rest.dtos.utils.FilterUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

/**
 * Created by eric on 05/06/2017.
 */
@RestController

public class TagRestController implements TagRestApi {

    protected static final Logger logger = LoggerFactory.getLogger(TagRestController.class);

    private final TagService tagService;

    private final TagMapper tagMapper;

    private final FilterUtil filterUtil;

    private final TagSpecification tagSpecification;

    @Autowired
    public TagRestController(TagService tagService, TagMapper tagMapper, FilterUtil dtoUtil, TagSpecification tagSpecification) {
        this.tagService = tagService;
        this.tagMapper = tagMapper;
        this.filterUtil = dtoUtil;
        this.tagSpecification = tagSpecification;
    }

    @Override
    public Page<TagDto> getTagsByPAge(Principal principal, @RequestParam(value = "filters", required = false) String filterString, Pageable pageable) throws IOException {

        FilterDto[] filterDtos = filterUtil.stringToFilterDtos(filterString);
        Specification<Tag> specifications = tagSpecification.getSpecifications(filterDtos, principal);

        return tagMapper.pageToDto(tagService.findAllByPage(specifications, pageable));
    }

    @Override
    public TagDto getTagById(@PathVariable("id") Long id) {
        return tagMapper.modelToDto(tagService.findById(id).orElse(null));
    }

    @Override
    public TagDto saveTag(@RequestBody TagDto tagDto) {
        Tag tag = tagMapper.dtoToModel(tagDto);
        return tagMapper.modelToDto(tagService.save(tag));
    }


    @Override
    public Iterable<SuggestDto> getSuggestions(@RequestParam("queryText") String queryText) {
        final List<SuggestDto> suggestions = Lists.newArrayList();
        if (StringUtils.isNoneEmpty(queryText)) {
            tagMapper.modelsToSugestDtos(tagService.findByLibelleContaining(queryText)).forEach(suggestions::add);
        }
        return suggestions;
    }

}
