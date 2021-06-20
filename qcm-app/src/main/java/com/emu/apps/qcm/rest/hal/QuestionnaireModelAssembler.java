package com.emu.apps.qcm.rest.hal;

import com.emu.apps.qcm.rest.controllers.QuestionnaireRestController;
import com.emu.apps.qcm.rest.resources.QuestionnaireResource;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class QuestionnaireModelAssembler implements SimpleRepresentationModelAssembler <QuestionnaireResource> {


    /**
     * Define links to add to every individual {@link EntityModel}.
     *
     * @param resource
     */
    @Override
    public void addLinks(EntityModel <QuestionnaireResource> resource) {

        resource.add(
                linkTo(methodOn(QuestionnaireRestController.class).getQuestionnaireById(resource.getContent().getUuid())).withSelfRel());
        resource.add(linkTo(methodOn(QuestionnaireRestController.class).getQuestionsByQuestionnaireId(resource.getContent().getUuid(), null,null)).withRel("employees"));

    }

    /**
     * Define links to add to the {@link CollectionModel} collection.
     *
     * @param resources
     */

    @Override
    public void addLinks(CollectionModel <EntityModel <QuestionnaireResource>> resources) {


    }
}
