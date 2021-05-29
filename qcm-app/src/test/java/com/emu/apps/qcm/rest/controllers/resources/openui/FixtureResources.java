package com.emu.apps.qcm.rest.controllers.resources.openui;

import com.emu.apps.qcm.rest.controllers.resources.CategoryResources;
import com.emu.apps.qcm.rest.controllers.resources.QuestionResources;
import com.emu.apps.qcm.rest.controllers.resources.QuestionnaireResources;
import com.emu.apps.qcm.rest.controllers.resources.ResponseResources;
import com.emu.apps.qcm.rest.controllers.resources.TagResources;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

import static java.util.UUID.randomUUID;

public class FixtureResources {


    public CategoryResources createCategory() {

        var resources = new CategoryResources();
        resources.setUuid(randomUUID().toString());
        resources.setLibelle("Libelle");
        resources.setUserId("UserId");

        resources.setVersion(1L);
        resources.setDateCreation(ZonedDateTime.now());
        resources.setDateModification(ZonedDateTime.now());

        return resources;
    }

    private ResponseResources createResponseResources() {
        var response = new ResponseResources();
        response.setUuid(randomUUID().toString());
        response.setResponseText("Reponse");
        response.setGood(Boolean.TRUE);
        response.setNumber(1L);
        response.setVersion(1L);

        return response;

    }

    public QuestionResources createQuestion() {
        var q = new QuestionResources();

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


    public TagResources createQuestionnaireTag() {

        var qt = new TagResources();

        qt.setUuid(randomUUID().toString());
        qt.setLibelle("Libelle");

        return qt;
    }

    public TagResources createQuestionTag() {

        var qt = new TagResources();

        qt.setUuid(randomUUID().toString());
        qt.setLibelle("Libelle");

        return qt;
    }

    public QuestionnaireResources createQuestionnaire() {
        var q = new QuestionnaireResources();

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
