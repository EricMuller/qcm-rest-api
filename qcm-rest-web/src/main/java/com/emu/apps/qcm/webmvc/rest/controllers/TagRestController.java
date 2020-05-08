package com.emu.apps.qcm.webmvc.rest.controllers;


import com.emu.apps.qcm.web.dtos.TagDto;
import com.emu.apps.qcm.business.TagDelegate;
import com.emu.apps.qcm.webmvc.rest.TagRestApi;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;

/**
 * Created by eric on 05/06/2017.
 */
@RestController
@Profile("webmvc")
public class TagRestController implements TagRestApi {

    private TagDelegate tagDelegate;

    public TagRestController(TagDelegate tagDelegate) {
        this.tagDelegate = tagDelegate;
    }

    @Override
    public Page <TagDto> getTagsByPAge(@RequestParam(value = "search", required = false) String search, Pageable pageable, Principal principal) throws IOException {

        return tagDelegate.getTagsByPAge(search, pageable, principal);
    }

    @Override
    public TagDto getTagById(@PathVariable("id") Long id) {
        return tagDelegate.getTagById(id);
    }

    @Override
    public TagDto saveTag(@RequestBody TagDto tagDto) {
        return tagDelegate.saveTag(tagDto);
    }


}
