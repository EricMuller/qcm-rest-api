package com.emu.apps.qcm.rest.controllers.secured.hal;

import com.emu.apps.qcm.rest.controllers.secured.QuestionRestController;
import com.emu.apps.qcm.rest.controllers.secured.resources.TagResource;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagModelAssembler implements SimpleRepresentationModelAssembler <TagResource> {


    /**
     * Define links to add to every individual {@link EntityModel}.
     *
     * @param resource
     */


    @Override
    public void addLinks(EntityModel <TagResource> resource) {

        resource.add(
                linkTo(methodOn(QuestionRestController.class).getTags(null, null)).withSelfRel());

    }

    /**
     * Define links to add to the {@link CollectionModel} collection.
     *
     * @param resources
     */

    @Override
    public void addLinks(CollectionModel <EntityModel <TagResource>> resources) {


    }
}
