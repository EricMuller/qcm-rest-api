package com.emu.apps.qcm.webmvc.rest.controllers;


import com.emu.apps.qcm.business.TagDelegate;
import com.emu.apps.qcm.web.dtos.TagDto;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

import static com.emu.apps.qcm.webmvc.rest.RestMapping.TAGS;

/**
 * Created by eric on 05/06/2017.
 */
@RestController
@Profile("webmvc")
@RequestMapping(value = TAGS, produces = MediaType.APPLICATION_JSON_VALUE)
public class TagRestController {

    private TagDelegate tagDelegate;

    public TagRestController(TagDelegate tagDelegate) {
        this.tagDelegate = tagDelegate;
    }

    @GetMapping
    @ResponseBody
    public Page <TagDto> getTagsByPAge(@RequestParam(value = "search", required = false) String search, Pageable pageable, Principal principal) throws IOException {
        return tagDelegate.getTagsByPAge(search, pageable, principal);
    }

    @GetMapping(value = "{id}")
    @ResponseBody
    public TagDto getTagById(@PathVariable("id") Long id) {
        return tagDelegate.getTagById(id);
    }

    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.POST})
    @ResponseBody
    public TagDto saveTag(@RequestBody TagDto tagDto) {
        return tagDelegate.saveTag(tagDto);
    }

}
