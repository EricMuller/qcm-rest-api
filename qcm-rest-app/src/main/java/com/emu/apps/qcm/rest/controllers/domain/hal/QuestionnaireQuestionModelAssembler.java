package com.emu.apps.qcm.rest.controllers.domain.hal;

import com.emu.apps.qcm.rest.controllers.domain.QuestionnaireRestController;
import com.emu.apps.qcm.rest.controllers.domain.resources.QuestionnaireQuestionResource;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class QuestionnaireQuestionModelAssembler implements SimpleRepresentationModelAssembler <QuestionnaireQuestionResource> {


    /**
     * Define links to add to every individual {@link EntityModel}.
     *
     * @param resource
     */
    @Override
    public void addLinks(EntityModel <QuestionnaireQuestionResource> resource) {

        resource.add(
                linkTo(methodOn(QuestionnaireRestController.class).getQuestionnaireQuestionById(resource.getContent().getQuestionnaireUuid(), resource.getContent().getUuid())).withSelfRel());

    }

    /**
     * Define links to add to the {@link CollectionModel} collection.
     *
     * @param resources
     */

    @Override
    public void addLinks(CollectionModel <EntityModel <QuestionnaireQuestionResource>> resources) {


    }
}
