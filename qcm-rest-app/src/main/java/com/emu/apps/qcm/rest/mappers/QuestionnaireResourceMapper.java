package com.emu.apps.qcm.rest.mappers;

import com.emu.apps.qcm.domain.mappers.AccountIdMapper;
import com.emu.apps.qcm.domain.mappers.CategoryIdMapper;
import com.emu.apps.qcm.domain.mappers.QuestionIdMapper;
import com.emu.apps.qcm.domain.mappers.QuestionnaireIdMapper;
import com.emu.apps.qcm.domain.mappers.TagIdMapper;
import com.emu.apps.qcm.domain.mappers.WebhookIdMapper;
import com.emu.apps.qcm.domain.model.Status;
import com.emu.apps.qcm.domain.model.category.Category;
import com.emu.apps.qcm.domain.model.question.Question;
import com.emu.apps.qcm.domain.model.question.QuestionTag;
import com.emu.apps.qcm.domain.model.question.QuestionWithTagsOnly;
import com.emu.apps.qcm.domain.model.question.Response;
import com.emu.apps.qcm.domain.model.questionnaire.Questionnaire;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireQuestion;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireTag;
import com.emu.apps.qcm.domain.model.tag.Tag;
import com.emu.apps.qcm.domain.model.upload.Upload;
import com.emu.apps.qcm.domain.model.account.Account;
import com.emu.apps.qcm.domain.model.webhook.WebHook;
import com.emu.apps.qcm.rest.controllers.secured.resources.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring",
        uses = {QuestionIdMapper.class, QuestionnaireIdMapper.class, AccountIdMapper.class, CategoryIdMapper.class,
                WebhookIdMapper.class, TagIdMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuestionnaireResourceMapper {

    @ResourceId
    QuestionnaireResource questionnaireToResources(Questionnaire questionnaire);

    Questionnaire questionnaireToModel(QuestionnaireResource questionnaireResource);

    Questionnaire questionnaireToModel(String id, QuestionnaireResource questionnaireResource);

    TagResource questionnaireTagToResources(QuestionnaireTag questionnaireTag);

    QuestionnaireTag questionnaireTagToModel(TagResource tagResource);

    @ResourceId
    CategoryResource categoryToResources(Category category);

    @ModelId
    Category categoryToModel(CategoryResource categoryResource);

    @Mapping(target = "uuid", source = "questionnaireQuestion.id")
    @Mapping(target = "questionnaireUuid", source = "questionnaireUuid")
    QuestionnaireQuestionResource questionnaireQuestionToResources(QuestionnaireQuestion questionnaireQuestion, String questionnaireUuid);

    Question questionToModel(QuestionResource questionResource);

    Question questionToModel(String id, QuestionResource questionResource);

    @ResourceId
    QuestionResource questionToResources(Question question);

    Response responseToModel(ResponseResource responseResource);

    @ResourceId
    OwnerResource accountToOwner(Account account);

    ResponseResource responseToResources(Response response);

    TagResource questionTagToResources(QuestionTag questionTag);

    QuestionTag questionTagToModel(TagResource tagResource);

    @ResourceId
    SearchQuestionResource questionTagsToResources(QuestionWithTagsOnly questionWithTagsOnly);

    @ResourceId
    TagResource tagToResources(Tag tag);

    Tag tagToModel(String id, TagResource tagResource);

    Tag tagToModel(TagResource tagResource);

    Iterable <TagResource> tagsToResources(Iterable <Tag> tags);

    UploadResource uploadToResources(Upload upload);

    Upload uploadToModel(UploadResource uploadResource);

    @ResourceId
    AccountResource accountToResources(Account account);

    @ModelId
    Account accountToModel(AccountResource accountResource);

    @ResourceId
    WebHookResource webhookToResources(WebHook webHook);

    WebHook webhookToModel(WebHookResource webHookResource);

    WebHook webhookToModel(String id, WebHookResource webHookResource);


    Iterable <CategoryResource> categoriesToResources(Iterable <Category> categories);

    Iterable <SearchQuestionResource> questionTagsToResources(Page <QuestionWithTagsOnly> page);

    default Page <WebHookResource> webhookToResources(Page <WebHook> page) {
        return page.map(this::webhookToResources);
    }


    default Page <UploadResource> uploadToResources(Page <Upload> page) {
        return page.map(this::uploadToResources);
    }

    default Page <TagResource> tagToResources(Page <Tag> page) {
        return page.map(this::tagToResources);
    }


    default  Page <TagResource> pageTagsToResources(Page <Tag> page){
        return page.map(this::tagToResources);
    }

    default Page <SearchQuestionResource> pageQuestionTagsToResources(Page <QuestionWithTagsOnly> page) {
        return page.map(this::questionTagsToResources);
    }

    default Page <QuestionnaireResource> questionnaireToResources(Page <Questionnaire> page) {
        return page.map(this::questionnaireToResources);
    }

    default Page <QuestionnaireQuestionResource> questionnaireQuestionToResources(Page <QuestionnaireQuestion> page, String questionnaireUuid) {
        return page.map(q -> this.questionnaireQuestionToResources(q, questionnaireUuid));
    }

    default Status getByName(String name) {
        return Status.getByName(name);
    }

}
