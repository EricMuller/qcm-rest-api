package com.emu.apps.qcm.rest.controllers.domain.hal;

import com.emu.apps.qcm.rest.controllers.domain.AccountRestController;
import com.emu.apps.qcm.rest.controllers.domain.QuestionRestController;
import com.emu.apps.qcm.rest.controllers.domain.resources.QuestionResource;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class QuestionModelAssembler implements SimpleRepresentationModelAssembler <QuestionResource> {


    /**
     * Define links to add to every individual {@link EntityModel}.
     *
     * @param resource
     */
    @Override
    public void addLinks(EntityModel <QuestionResource> resource) {

        resource.add(
                linkTo(methodOn(QuestionRestController.class).getQuestionByUuid( (resource.getContent() != null ) ? resource.getContent().getUuid() :  "")

                ).withSelfRel());

        resource.add(linkTo(methodOn(AccountRestController.class).getUserById(resource.getContent().getOwner().getUuid())).withRel("owner"));

    }

    /**
     * Define links to add to the {@link CollectionModel} collection.
     *
     * @param resources
     */

    @Override
    public void addLinks(CollectionModel <EntityModel <QuestionResource>> resources) {

        // nope
    }
}
