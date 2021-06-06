package com.emu.apps.qcm.rest.resources.openui;

import com.emu.apps.qcm.rest.resources.CategoryResource;
import com.emu.apps.qcm.rest.resources.QuestionResource;
import com.emu.apps.qcm.rest.resources.QuestionnaireResource;
import com.emu.apps.qcm.rest.resources.ResponseResource;
import com.emu.apps.qcm.rest.resources.TagResource;

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

        q.setTags(Set.of(createQuestionTag()));

        q.setVersion(1L);
        q.setDateCreation(ZonedDateTime.now());
        q.setDateModification(ZonedDateTime.now());

        return q;
    }


    public TagResource createQuestionnaireTag() {

        var qt = new TagResource();

        qt.setUuid(randomUUID().toString());
        qt.setLibelle("Libelle");

        return qt;
    }

    public TagResource createQuestionTag() {

        var qt = new TagResource();

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
