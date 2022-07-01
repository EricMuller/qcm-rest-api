package com.emu.apps.qcm.rest.controllers.domain.jsonview;

import com.emu.apps.qcm.rest.controllers.domain.resources.CategoryResource;
import com.emu.apps.qcm.rest.controllers.domain.resources.QuestionResource;
import com.emu.apps.qcm.rest.controllers.domain.resources.QuestionTagResource;
import com.emu.apps.qcm.rest.controllers.domain.resources.QuestionnaireResource;
import com.emu.apps.qcm.rest.controllers.domain.resources.QuestionnaireTagResource;
import com.emu.apps.qcm.rest.controllers.domain.resources.ResponseResource;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

import static java.util.UUID.randomUUID;

public class FixtureResources {


    public CategoryResource createCategory() {

        var resources = new CategoryResource();
        resources.setUuid(randomUUID().toString());
        resources.setLibelle("Libelle");
        resources.setUserId("UserId");

        resources.setVersion(1L);
        resources.setDateCreation(ZonedDateTime.now());
        resources.setDateModification(ZonedDateTime.now());

        return resources;
    }

    private ResponseResource createResponseResources() {
        var response = new ResponseResource();
        response.setUuid(randomUUID().toString());
        response.setResponseText("Reponse");
        response.setGood(Boolean.TRUE);
        response.setNumber(1L);

        return response;

    }

    public QuestionResource createQuestion() {
        var questionResource = new QuestionResource();

        questionResource.setUuid(randomUUID().toString());
        questionResource.setType("Type");
        questionResource.setCategory(createCategory());
        questionResource.setStatus("Status");
        questionResource.setQuestionText("Question");
        questionResource.setResponses(List.of(createResponseResources()));
        questionResource.setTip("Tip");

        questionResource.setTags(List.of(createQuestionTag()));

        questionResource.setVersion(1L);
        questionResource.setDateCreation(ZonedDateTime.now());
        questionResource.setDateModification(ZonedDateTime.now());

        return questionResource;
    }


    public QuestionnaireTagResource createQuestionnaireTag() {

        var questionnaireTagResource = new QuestionnaireTagResource();

        questionnaireTagResource.setUuid(randomUUID().toString());
        questionnaireTagResource.setLibelle("Libelle");

        return questionnaireTagResource;
    }

    public QuestionTagResource createQuestionTag() {

        var questionTagResource = new QuestionTagResource();

        questionTagResource.setUuid(randomUUID().toString());
        questionTagResource.setLibelle("Libelle");

        return questionTagResource;
    }


    public QuestionnaireResource createQuestionnaire() {
        var questionnaireResource = new QuestionnaireResource();

        questionnaireResource.setUuid(randomUUID().toString());
        questionnaireResource.setCategory(createCategory());
        questionnaireResource.setStatus("Status");
        questionnaireResource.setTitle("Title");
        questionnaireResource.setDescription("Description");
        questionnaireResource.setWebsite("WebSite");

        questionnaireResource.setTags(Set.of(createQuestionnaireTag()));

        questionnaireResource.setVersion(1L);
        questionnaireResource.setDateCreation(ZonedDateTime.now());
        questionnaireResource.setDateModification(ZonedDateTime.now());
        questionnaireResource.setCreatedBy("CreateBy");

        return questionnaireResource;
    }


}
