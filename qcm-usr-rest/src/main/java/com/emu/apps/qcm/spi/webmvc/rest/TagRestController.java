package com.emu.apps.qcm.spi.webmvc.rest;


import com.emu.apps.qcm.aggregates.Tag;
import com.emu.apps.qcm.repositories.TagRepository;
import com.emu.apps.shared.security.AuthentificationContextHolder;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.emu.apps.qcm.spi.webmvc.rest.ApiRestMappings.PUBLIC_API;
import static com.emu.apps.qcm.spi.webmvc.rest.ApiRestMappings.TAGS;
import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * Created by eric on 05/06/2017.
 */
@RestController
@Profile("webmvc")
@RequestMapping(value = PUBLIC_API + TAGS, produces = MediaType.APPLICATION_JSON_VALUE)
@io.swagger.v3.oas.annotations.tags.Tag(name = "Tag")
public class TagRestController {

    private final TagRepository tagRepository;

    public TagRestController(TagRepository tagServicePort) {
        this.tagRepository = tagServicePort;
    }

    @GetMapping
    @ResponseBody
    @PageableAsQueryParam
    public Page <Tag> getTags(@RequestParam(value = "search", required = false) String search,
                              @Parameter(hidden = true)
                              @PageableDefault(direction = DESC, sort = {"dateModification"}, size = 100) Pageable pageable) throws IOException {

        return tagRepository.getTags(search, pageable, AuthentificationContextHolder.getUser());
    }

    @GetMapping(value = "{uuid}")
    @ResponseBody
    public Tag getTagByUuid(@PathVariable("uuid") String uuid) {
        return tagRepository.getTagByUuid(uuid);
    }

    @PostMapping
    @ResponseBody
    public Tag createTag(@RequestBody Tag tagDto) {
        return tagRepository.saveTag(tagDto);
    }


    @PutMapping
    @ResponseBody
    public Tag updateTag(@RequestBody Tag tagDto) {
        return tagRepository.saveTag(tagDto);
    }

}
