package com.emu.apps.qcm.rest.controllers.domain.hal;

import com.emu.apps.qcm.rest.controllers.domain.UploadRestController;
import com.emu.apps.qcm.rest.controllers.domain.resources.UploadResource;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UploadModelAssembler implements SimpleRepresentationModelAssembler <UploadResource> {


    /**
     * Define links to add to every individual {@link EntityModel}.
     *
     * @param resource
     */
    @Override
    public void addLinks(EntityModel <UploadResource> resource) {

        resource.add(
                linkTo(methodOn(UploadRestController.class).getUploadByUuid(resource.getContent().getUuid())).withSelfRel());

    }

    /**
     * Define links to add to the {@link CollectionModel} collection.
     *
     * @param resources
     */

    @Override
    public void addLinks(CollectionModel <EntityModel <UploadResource>> resources) {


    }


}
