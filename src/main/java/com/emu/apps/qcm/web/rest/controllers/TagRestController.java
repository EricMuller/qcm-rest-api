package com.emu.apps.qcm.web.rest.controllers;


import com.emu.apps.qcm.services.TagService;
import com.emu.apps.qcm.services.jpa.entity.tags.Tag;
import com.emu.apps.qcm.services.jpa.specifications.TagSpecificationBuilder;
import com.emu.apps.qcm.web.rest.TagRestApi;
import com.emu.apps.qcm.web.rest.dtos.SuggestDto;
import com.emu.apps.qcm.web.rest.dtos.TagDto;
import com.emu.apps.qcm.web.rest.mappers.TagMapper;
import com.emu.apps.shared.parsers.rsql.Criteria;
import com.emu.apps.shared.parsers.rsql.RequestUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

/**
 * Created by eric on 05/06/2017.
 */
@RestController

public class TagRestController implements TagRestApi {

    protected static final Logger logger = LoggerFactory.getLogger(TagRestController.class);

    private final TagService tagService;

    private final TagMapper tagMapper;

    @Autowired
    public TagRestController(TagService tagService, TagMapper tagMapper) {
        this.tagService = tagService;
        this.tagMapper = tagMapper;

    }

    @Override
    public Page<TagDto> getTagsByPAge(@RequestParam(value = "search", required = false) String search, Pageable pageable, Principal principal) throws IOException {

        Criteria[] criterias = RequestUtil.toCriteria(search);
        Optional<String> firstLetter = RequestUtil.getAttribute("firstLetter", criterias);

        TagSpecificationBuilder tagSpecificationBuilder = new TagSpecificationBuilder()
                .setPrincipal(principal.getName())
                .setLetter(firstLetter.isPresent() ? firstLetter.get() : null);


        return tagMapper.pageToDto(tagService.findAllByPage(tagSpecificationBuilder.build(), pageable));
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





}
