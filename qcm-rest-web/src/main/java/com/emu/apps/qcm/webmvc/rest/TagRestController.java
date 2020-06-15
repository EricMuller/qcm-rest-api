package com.emu.apps.qcm.webmvc.rest;


import com.emu.apps.qcm.domain.ports.TagServicePort;
import com.emu.apps.qcm.domain.dtos.TagDto;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

import static com.emu.apps.qcm.webmvc.rest.ApiRestMappings.PROTECTED_TAGS;

/**
 * Created by eric on 05/06/2017.
 */
@RestController
@Profile("webmvc")
@RequestMapping(value = PROTECTED_TAGS, produces = MediaType.APPLICATION_JSON_VALUE)
public class TagRestController {

    private final TagServicePort tagServicePort;

    public TagRestController(TagServicePort tagServicePort) {
        this.tagServicePort = tagServicePort;
    }

    @GetMapping
    @ResponseBody
    public Page <TagDto> getTagsByPAge(@RequestParam(value = "search", required = false) String search, Pageable pageable, Principal principal) throws IOException {
        return tagServicePort.getTagsByPAge(search, pageable, principal);
    }

    @GetMapping(value = "{id}")
    @ResponseBody
    public TagDto getTagById(@PathVariable("id") Long id) {
        return tagServicePort.getTagById(id);
    }

    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.POST})
    @ResponseBody
    public TagDto saveTag(@RequestBody TagDto tagDto) {
        return tagServicePort.saveTag(tagDto);
    }

}
