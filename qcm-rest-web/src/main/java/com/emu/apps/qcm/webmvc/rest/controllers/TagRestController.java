package com.emu.apps.qcm.webmvc.rest.controllers;


import com.emu.apps.qcm.webmvc.services.TagService;
import com.emu.apps.qcm.webmvc.services.jpa.specifications.TagSpecificationBuilder;
import com.emu.apps.shared.security.PrincipalUtils;
import com.emu.apps.qcm.web.dtos.TagDto;
import com.emu.apps.qcm.web.mappers.TagMapper;
import com.emu.apps.qcm.webmvc.rest.TagRestApi;
import com.emu.apps.shared.parsers.rsql.CriteriaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

/**
 * Created by eric on 05/06/2017.
 */
@RestController
@Profile("webmvc")
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

        var criterias = CriteriaUtils.toCriteria(search);
        Optional<String> firstLetter = CriteriaUtils.getAttribute("firstLetter", criterias);

        var tagSpecificationBuilder = new TagSpecificationBuilder()
                .setPrincipal(PrincipalUtils.getEmail(principal))
                .setLetter(firstLetter.isPresent() ? firstLetter.get() : null);


        return tagMapper.pageToDto(tagService.findAllByPage(tagSpecificationBuilder.build(), pageable));
    }

    @Override
    public TagDto getTagById(@PathVariable("id") Long id) {
        return tagMapper.modelToDto(tagService.findById(id).orElse(null));
    }

    @Override
    public TagDto saveTag(@RequestBody TagDto tagDto) {
        var tag = tagMapper.dtoToModel(tagDto);
        return tagMapper.modelToDto(tagService.save(tag));
    }





}
