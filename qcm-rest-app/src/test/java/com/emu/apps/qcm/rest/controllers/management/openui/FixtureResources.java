package com.emu.apps.qcm.rest.controllers.management.openui;

import com.emu.apps.qcm.rest.controllers.management.resources.CategoryResource;
import com.emu.apps.qcm.rest.controllers.management.resources.QuestionResource;
import com.emu.apps.qcm.rest.controllers.management.resources.QuestionTagResource;
import com.emu.apps.qcm.rest.controllers.management.resources.QuestionnaireResource;
import com.emu.apps.qcm.rest.controllers.management.resources.QuestionnaireTagResource;
import com.emu.apps.qcm.rest.controllers.management.resources.ResponseResource;

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
        response.setVersion(1L);

        return response;

    }

    public QuestionResource createQuestion() {
        var q = new QuestionResource();

        q.setUuid(randomUUID().toString());
        q.setType("Type");
        q.setCategory(createCategory());
        q.setStatus("Status");
        q.setQuestionText("Question");
        q.setResponses(List.of(createResponseResources()));
        q.setTip("Tip");

        q.setTags(List.of(createQuestionTag()));

        q.setVersion(1L);
        q.setDateCreation(ZonedDateTime.now());
        q.setDateModification(ZonedDateTime.now());

        return q;
    }


    public QuestionnaireTagResource createQuestionnaireTag() {

        var qt = new QuestionnaireTagResource();

        qt.setUuid(randomUUID().toString());
        qt.setLibelle("Libelle");

        return qt;
    }

    public QuestionTagResource createQuestionTag() {

        var qt = new QuestionTagResource();

        qt.setUuid(randomUUID().toString());
        qt.setLibelle("Libelle");

        return qt;
    }


    public QuestionnaireResource createQuestionnaire() {
        var q = new QuestionnaireResource();

        q.setUuid(randomUUID().toString());
        q.setCategory(createCategory());
        q.setStatus("Status");
        q.setTitle("Title");
        q.setDescription("Description");
        q.setWebsite("WebSite");

        q.setTags(Set.of(createQuestionnaireTag()));

        q.setVersion(1L);
        q.setDateCreation(ZonedDateTime.now());
        q.setDateModification(ZonedDateTime.now());
        q.setCreatedBy("CreateBy");

        return q;
    }


}
