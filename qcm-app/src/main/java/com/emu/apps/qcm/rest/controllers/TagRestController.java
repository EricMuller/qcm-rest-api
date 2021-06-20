package com.emu.apps.qcm.rest.controllers;


import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.tag.TagId;
import com.emu.apps.qcm.domain.model.tag.TagRepository;
import com.emu.apps.qcm.rest.mappers.QuestionnaireResourceMapper;
import com.emu.apps.qcm.rest.resources.TagResource;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
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
import java.util.List;

import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.PUBLIC_API;
import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.TAGS;
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

    private final QuestionnaireResourceMapper questionnaireResourceMapper;

    public TagRestController(TagRepository tagRepository, QuestionnaireResourceMapper questionnaireResourceMapper) {
        this.tagRepository = tagRepository;
        this.questionnaireResourceMapper = questionnaireResourceMapper;
    }

    @GetMapping
    @ResponseBody
    @PageableAsQueryParam
    public PageTag getTags(@RequestParam(value = "search", required = false) String search,
                           @Parameter(hidden = true)
                           @PageableDefault(direction = DESC, sort = {"dateModification"}, size = 100) Pageable pageable) throws IOException {

        Page <TagResource> tagResourcesPage =
                questionnaireResourceMapper.tagToResources(tagRepository.getTags(search, pageable, new PrincipalId(getPrincipal())));

        return new PageTag(tagResourcesPage.getContent(), pageable, tagResourcesPage.getContent().size());
    }

    @GetMapping(value = "{uuid}")
    @ResponseBody
    public EntityModel <TagResource> getTagByUuid(@PathVariable("uuid") String uuid) {
        return EntityModel.of(questionnaireResourceMapper.tagToResources(tagRepository.getTagById(new TagId(uuid))));
    }

    @PostMapping
    @ResponseBody
    public EntityModel <TagResource> createTag(@JsonView(TagResource.Create.class) @RequestBody TagResource tagResource) {
        var tag = questionnaireResourceMapper.tagToModel(tagResource);
        return EntityModel.of(questionnaireResourceMapper.tagToResources(tagRepository.saveTag(tag)));
    }

    @PutMapping(value = "{uuid}")
    @ResponseBody
    public TagResource updateTag(@PathVariable("uuid") String uuid,
                                 @JsonView(TagResource.Update.class) @RequestBody TagResource tagResource) {
        var tag = questionnaireResourceMapper.tagToModel(uuid, tagResource);
        return questionnaireResourceMapper.tagToResources(tagRepository.saveTag(tag));
    }

    /**
     * Springdoc issue
     */
    class PageTag extends PageImpl <TagResource> {
        public PageTag(List <TagResource> content, Pageable pageable, long total) {
            super(content, pageable, total);
        }
    }

}
