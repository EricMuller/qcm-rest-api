package com.emu.apps.qcm.rest.controllers.publicized.hal;

import com.emu.apps.qcm.rest.controllers.publicized.AccountRestPublicController;
import com.emu.apps.qcm.rest.controllers.publicized.QuestionnairePublicRestController;
import com.emu.apps.qcm.rest.controllers.publicized.resources.ApiResource;
import com.emu.apps.qcm.rest.controllers.secured.QuestionnaireRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static java.util.Objects.nonNull;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ApiAssembler implements SimpleRepresentationModelAssembler <ApiResource> {


    /**
     * Define links to add to every individual {@link EntityModel}.
     *
     * @param resource
     */
    @Override
    public void addLinks(EntityModel <ApiResource> resource) {

        resource.add(
                linkTo(AccountRestPublicController.class).withRel("me"));

        resource.add(linkTo(methodOn(QuestionnairePublicRestController.class).getPublishedQuestionnaires(null)).withRel("questionnaires"));
        resource.add(linkTo(methodOn(QuestionnairePublicRestController.class).getPublishedCategories()).withRel("categories"));
        resource.add(linkTo(methodOn(QuestionnairePublicRestController.class).getPublishedTags()).withRel("tags"));


        var authorized = nonNull(resource) && nonNull(resource.getContent()) && nonNull(resource.getContent().getPrincipal());

        if (authorized) {
            resource.add(linkTo(QuestionnaireRestController.class).withRel("my_questionnaires"));
        }

    }

    /**
     * Define links to add to the {@link CollectionModel} collection.
     *
     * @param resources
     */

    @Override
    public void addLinks(CollectionModel <EntityModel <ApiResource>> resources) {


    }
}
