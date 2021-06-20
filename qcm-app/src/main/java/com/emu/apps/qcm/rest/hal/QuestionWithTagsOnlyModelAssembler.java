package com.emu.apps.qcm.rest.hal;

import com.emu.apps.qcm.rest.controllers.QuestionRestController;
import com.emu.apps.qcm.rest.resources.QuestionWithTagsOnlyResource;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class QuestionWithTagsOnlyModelAssembler implements SimpleRepresentationModelAssembler <QuestionWithTagsOnlyResource> {


    /**
     * Define links to add to every individual {@link EntityModel}.
     *
     * @param resource
     */
    @Override
    public void addLinks(EntityModel <QuestionWithTagsOnlyResource> resource) {

        resource.add(
                linkTo(methodOn(QuestionRestController.class).getQuestionByUuid(resource.getContent().getUuid())).withSelfRel());

    }

    /**
     * Define links to add to the {@link CollectionModel} collection.
     *
     * @param resources
     */

    @Override
    public void addLinks(CollectionModel <EntityModel <QuestionWithTagsOnlyResource>> resources) {


    }
}
