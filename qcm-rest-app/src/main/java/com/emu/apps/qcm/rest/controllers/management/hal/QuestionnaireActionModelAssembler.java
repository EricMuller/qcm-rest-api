package com.emu.apps.qcm.rest.controllers.management.hal;

import com.emu.apps.qcm.rest.controllers.management.QuestionnaireRestController;
import com.emu.apps.qcm.rest.controllers.management.resources.ActionResource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class QuestionnaireActionModelAssembler implements SimpleRepresentationModelAssembler <ActionResource> {


    /**
     * Define links to add to every individual {@link EntityModel}.
     *
     * @param resource
     */
    @Override
    public void addLinks(EntityModel <ActionResource> resource) {


        if (StringUtils.equals("export", resource.getContent().getAction())) {
            resource.add(
                    linkTo(methodOn(QuestionnaireRestController.class).createJsonExportByQuestionnaireUuid(resource.getContent().getUuid())).withSelfRel()
                    );
        }
        if (StringUtils.equals("report", resource.getContent().getAction())) {
            resource.add(
                    linkTo(methodOn(QuestionnaireRestController.class ).createByteArrayReportByQuestionnaireUuid(resource.getContent().getUuid(), "json")).withSelfRel(),
                    linkTo(methodOn(QuestionnaireRestController.class).createByteArrayReportByQuestionnaireUuid(resource.getContent().getUuid(), "pdf")).withSelfRel(),
                    linkTo(methodOn(QuestionnaireRestController.class).createByteArrayReportByQuestionnaireUuid(resource.getContent().getUuid(), "docx")).withSelfRel()
            );
        }


    }

    /**
     * Define links to add to the {@link CollectionModel} collection.
     *
     * @param resources
     */

    @Override
    public void addLinks(CollectionModel <EntityModel <ActionResource>> resources) {


    }


}
