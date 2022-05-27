package com.emu.apps.qcm.rest.controllers.management;


import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.tag.TagId;
import com.emu.apps.qcm.domain.model.tag.TagRepository;
import com.emu.apps.qcm.rest.controllers.management.resources.TagResource;
import com.emu.apps.qcm.rest.mappers.QcmResourceMapper;
import com.fasterxml.jackson.annotation.JsonView;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springdoc.core.converters.models.PageableAsQueryParam;
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

import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.MANAGEMENT_API;
import static com.emu.apps.qcm.rest.controllers.ApiRestMappings.TAGS;
import static com.emu.apps.shared.security.AuthentificationContextHolder.getPrincipal;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by eric on 05/06/2017.
 */
@RestController
@RequestMapping(value = MANAGEMENT_API + TAGS, produces = APPLICATION_JSON_VALUE)
@io.swagger.v3.oas.annotations.tags.Tag(name = "Tag")
@Timed("tags")
public class TagRestController {

    private final TagRepository tagRepository;

    private final QcmResourceMapper qcmResourceMapper;

    public TagRestController(TagRepository tagRepository, QcmResourceMapper qcmResourceMapper) {
        this.tagRepository = tagRepository;
        this.qcmResourceMapper = qcmResourceMapper;
    }

    @GetMapping
    @ResponseBody
    @PageableAsQueryParam
    @Timed(value = "tags.getTags", longTask = true)
    public PageTag getTags(@RequestParam(value = "search", required = false) String search,
                           @Parameter(hidden = true)
                           @PageableDefault(direction = DESC, sort = {"dateModification"}, size = 100) Pageable pageable) throws IOException {

        Page <TagResource> tagResourcesPage =
                qcmResourceMapper.tagToTagResources(tagRepository.getTags(search, pageable, PrincipalId.of(getPrincipal())));

        return new PageTag(tagResourcesPage.getContent(), pageable, tagResourcesPage.getContent().size());
    }

    @GetMapping(value = "{uuid}")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Object", content = @Content(schema = @Schema(name = "TagResource", implementation = TagResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")})
    @Timed(value = "tags.getTagByUuid")
    public EntityModel <TagResource> getTagByUuid(@PathVariable("uuid") String uuid) {
        return EntityModel.of(qcmResourceMapper.tagToTagResources(tagRepository.getTagById(new TagId(uuid))));
    }

    @PostMapping
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Object created", content = @Content(schema = @Schema(name = "TagResource", implementation = TagResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")})
    @Timed(value = "tags.createTag")
    public EntityModel <TagResource> createTag(@JsonView(TagResource.Create.class) @RequestBody TagResource tagResource) {
        var tag = qcmResourceMapper.tagResourceToModel(tagResource);
        return EntityModel.of(qcmResourceMapper.tagToTagResources(tagRepository.saveTag(tag)));
    }

    @PutMapping(value = "{uuid}")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Object updated", content = @Content(schema = @Schema(name = "TagResource", implementation = TagResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")})
    @Timed(value = "tags.updateTag")
    public TagResource updateTag(@PathVariable("uuid") String uuid,
                                 @JsonView(TagResource.Update.class) @RequestBody TagResource tagResource) {
        var tag = qcmResourceMapper.tagResourceToModel(uuid, tagResource);
        return qcmResourceMapper.tagToTagResources(tagRepository.saveTag(tag));
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
