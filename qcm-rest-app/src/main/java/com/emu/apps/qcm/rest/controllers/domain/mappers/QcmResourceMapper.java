package com.emu.apps.qcm.rest.controllers.domain.mappers;

import com.emu.apps.qcm.domain.mappers.AccountIdMapper;
import com.emu.apps.qcm.domain.mappers.CategoryIdMapper;
import com.emu.apps.qcm.domain.mappers.QuestionIdMapper;
import com.emu.apps.qcm.domain.mappers.QuestionnaireIdMapper;
import com.emu.apps.qcm.domain.mappers.TagIdMapper;
import com.emu.apps.qcm.domain.mappers.WebhookIdMapper;
import com.emu.apps.qcm.domain.model.Status;
import com.emu.apps.qcm.domain.model.category.MpttCategory;
import com.emu.apps.qcm.domain.model.question.Question;
import com.emu.apps.qcm.domain.model.question.QuestionTag;
import com.emu.apps.qcm.domain.query.question.QuestionWithTagsOnly;
import com.emu.apps.qcm.domain.model.question.Response;
import com.emu.apps.qcm.domain.model.questionnaire.Questionnaire;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireQuestion;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireTag;
import com.emu.apps.qcm.domain.model.tag.Tag;
import com.emu.apps.qcm.domain.model.upload.Upload;
import com.emu.apps.qcm.domain.model.account.Account;
import com.emu.apps.qcm.domain.model.webhook.WebHook;
import com.emu.apps.qcm.rest.controllers.domain.resources.*;
import com.emu.apps.qcm.rest.controllers.services.search.SearchQuestionsByTagAndQcmId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring",
        uses = {QuestionIdMapper.class, QuestionnaireIdMapper.class, AccountIdMapper.class, CategoryIdMapper.class,
                WebhookIdMapper.class, TagIdMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QcmResourceMapper {

    @ResourceId
    QuestionnaireResource questionnaireToQuestionnaireResources(Questionnaire questionnaire);

    Questionnaire questionnaireResourceToModel(QuestionnaireResource questionnaireResource);

    Questionnaire questionnaireResourceToModel(String id, QuestionnaireResource questionnaireResource);

    TagResource questionnaireTagToResources(QuestionnaireTag questionnaireTag);

    QuestionnaireTag questionnaireTagToModel(TagResource tagResource);

    @ResourceId
    CategoryResource categoryToResources(MpttCategory mpttCategory);

    @ModelId
    MpttCategory categoryResourceToModel(CategoryResource categoryResource);

    @Mapping(target = "uuid", source = "questionnaireQuestion.id")
    @Mapping(target = "questionnaireUuid", source = "questionnaireUuid")
    QuestionnaireQuestionResource questionnaireQuestionToResources(QuestionnaireQuestion questionnaireQuestion, String questionnaireUuid);

    Question questionResourceToModel(QuestionResource questionResource);

    Question questionResourceToModel(String id, QuestionResource questionResource);

    @ResourceId
    QuestionResource questionToQuestionResource(Question question);

    Response responseResourceToModel(ResponseResource responseResource);

    @ResourceId
    OwnerResource accountToOwnerResource(Account account);

    ResponseResource responseToResource(Response response);

    TagResource questionTagToTagResource(QuestionTag questionTag);

    QuestionTag questionTagToModel(TagResource tagResource);

    @ResourceId
    SearchQuestionsByTagAndQcmId questionTagsToSearchResources(QuestionWithTagsOnly questionWithTagsOnly);

    @ResourceId
    TagResource tagToTagResources(Tag tag);

    Tag tagResourceToModel(String id, TagResource tagResource);

    Tag tagResourceToModel(TagResource tagResource);

    Iterable <TagResource> tagsToResources(Iterable <Tag> tags);

    UploadResource uploadToUploadResources(Upload upload);

    Upload uploadToModel(UploadResource uploadResource);

    @ResourceId
    AccountResource accountToAccountResource(Account account);

    @ModelId
    Account accountResourceToModel(AccountResource accountResource);

    @ResourceId
    WebHookResource webhookToWebhookResources(WebHook webHook);

    WebHook webhookResourceToModel(WebHookResource webHookResource);

    WebHook webhookResourceToModel(String id, WebHookResource webHookResource);


    Iterable <CategoryResource> categoriesToCategoryResources(Iterable <MpttCategory> categories);

    Iterable <SearchQuestionsByTagAndQcmId> questionTagsToSearchResources(Page <QuestionWithTagsOnly> page);

    default Page <WebHookResource> webhookToWebhookResources(Page <WebHook> page) {
        return page.map(this::webhookToWebhookResources);
    }


    default Page <UploadResource> uploadToUploadResources(Page <Upload> page) {
        return page.map(this::uploadToUploadResources);
    }

    default Page <TagResource> tagToTagResources(Page <Tag> page) {
        return page.map(this::tagToTagResources);
    }


    default  Page <TagResource> pageTagsToResources(Page <Tag> page){
        return page.map(this::tagToTagResources);
    }

    default Page <SearchQuestionsByTagAndQcmId> pageQuestionTagsToResources(Page <QuestionWithTagsOnly> page) {
        return page.map(this::questionTagsToSearchResources);
    }

    default Page <QuestionnaireResource> questionnaireToQuestionnaireResources(Page <Questionnaire> page) {
        return page.map(this::questionnaireToQuestionnaireResources);
    }

    default Page <QuestionnaireQuestionResource> questionnaireQuestionToResources(Page <QuestionnaireQuestion> page, String questionnaireUuid) {
        return page.map(q -> this.questionnaireQuestionToResources(q, questionnaireUuid));
    }

    default Status getByName(String name) {
        return Status.getByName(name);
    }

}
