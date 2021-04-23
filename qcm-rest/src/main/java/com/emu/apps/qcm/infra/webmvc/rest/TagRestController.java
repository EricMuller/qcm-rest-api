package com.emu.apps.qcm.infra.webmvc.rest;


import com.emu.apps.qcm.domain.model.tag.Tag;
import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.tag.TagId;
import com.emu.apps.qcm.domain.model.tag.TagRepository;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static com.emu.apps.qcm.infra.webmvc.rest.ApiRestMappings.PUBLIC_API;
import static com.emu.apps.qcm.infra.webmvc.rest.ApiRestMappings.TAGS;
import static com.emu.apps.shared.security.AuthentificationContextHolder.getPrincipal;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by eric on 05/06/2017.
 */
@RestController
@Profile("webmvc")
@RequestMapping(value = PUBLIC_API + TAGS, produces = APPLICATION_JSON_VALUE)
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

        return tagRepository.getTags(search, pageable, new PrincipalId(getPrincipal()));
    }

    @GetMapping(value = "{uuid}")
    @ResponseBody
    public Tag getTagByUuid(@PathVariable("uuid") String uuid) {
        return tagRepository.getTagById(new TagId(uuid));
    }

    @PostMapping
    @ResponseBody
    public Tag createTag(@RequestBody Tag tag) {
        return tagRepository.saveTag(tag);
    }


    @PutMapping
    @ResponseBody
    public Tag updateTag(@RequestBody Tag tag) {
        return tagRepository.saveTag(tag);
    }

}
