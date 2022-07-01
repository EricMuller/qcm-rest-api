package com.emu.apps.qcm.rest.controllers.domain.hal;

import com.emu.apps.qcm.rest.controllers.domain.UploadRestController;
import com.emu.apps.qcm.rest.controllers.domain.resources.ActionResource;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
    public class UploadActionModelAssembler implements SimpleRepresentationModelAssembler <ActionResource> {

        @Override
        public void addLinks(EntityModel <ActionResource> resource) {

            resource.add(
                    linkTo(methodOn(UploadRestController.class).importFileByUploadUuid(resource.getContent().getUuid())).withSelfRel());
        }
        @Override
        public void addLinks(CollectionModel <EntityModel <ActionResource>> resources) {


        }
    }
