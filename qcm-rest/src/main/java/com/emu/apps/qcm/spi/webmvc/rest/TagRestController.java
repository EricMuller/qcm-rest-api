package com.emu.apps.qcm.spi.webmvc.rest;


import com.emu.apps.qcm.api.models.Tag;
import com.emu.apps.qcm.domain.ports.TagServicePort;
import com.emu.apps.shared.security.AuthentificationContextHolder;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.emu.apps.qcm.spi.webmvc.rest.ApiRestMappings.PUBLIC_API;
import static com.emu.apps.qcm.spi.webmvc.rest.ApiRestMappings.TAGS;

/**
 * Created by eric on 05/06/2017.
 */
@RestController
@Profile("webmvc")
@RequestMapping(value = PUBLIC_API + TAGS, produces = MediaType.APPLICATION_JSON_VALUE)
@io.swagger.v3.oas.annotations.tags.Tag(name = "Tag")
public class TagRestController {

    private final TagServicePort tagServicePort;

    public TagRestController(TagServicePort tagServicePort) {
        this.tagServicePort = tagServicePort;
    }

    @GetMapping
    @ResponseBody
    public Page <Tag> getTags(@RequestParam(value = "search", required = false) String search,
                              @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                              @RequestParam(value = "count", defaultValue = "100", required = false) int size,
                              @RequestParam(value = "order", defaultValue = "DESC", required = false) Sort.Direction direction,
                              @RequestParam(value = "sort", defaultValue = "dateModification", required = false) String sortProperty) throws IOException {

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortProperty));

        return tagServicePort.getTags(search, pageable, AuthentificationContextHolder.getUser());
    }

    @GetMapping(value = "{uuid}")
    @ResponseBody
    public Tag getTagByUuid(@PathVariable("uuid") String uuid) {
        return tagServicePort.getTagByUuid(uuid);
    }

    @PostMapping
    @ResponseBody
    public Tag createTag(@RequestBody Tag tagDto) {
        return tagServicePort.saveTag(tagDto);
    }


    @PutMapping
    @ResponseBody
    public Tag updateTag(@RequestBody Tag tagDto) {
        return tagServicePort.saveTag(tagDto);
    }

}
