package com.emu.apps.qcm.rest.controllers.mappers;

import com.emu.apps.qcm.domain.mappers.AccountIdMapper;
import com.emu.apps.qcm.domain.mappers.CategoryIdMapper;
import com.emu.apps.qcm.domain.mappers.QuestionIdMapper;
import com.emu.apps.qcm.domain.mappers.QuestionnaireIdMapper;
import com.emu.apps.qcm.domain.mappers.TagIdMapper;
import com.emu.apps.qcm.domain.mappers.WebhookIdMapper;
import com.emu.apps.qcm.domain.model.category.Category;
import com.emu.apps.qcm.domain.model.question.Question;
import com.emu.apps.qcm.domain.model.question.QuestionTag;
import com.emu.apps.qcm.domain.model.question.QuestionTags;
import com.emu.apps.qcm.domain.model.question.Response;
import com.emu.apps.qcm.domain.model.questionnaire.Questionnaire;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireQuestion;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireTag;
import com.emu.apps.qcm.domain.model.tag.Tag;
import com.emu.apps.qcm.domain.model.upload.Upload;
import com.emu.apps.qcm.domain.model.user.Account;
import com.emu.apps.qcm.domain.model.webhook.WebHook;
import com.emu.apps.qcm.rest.controllers.resources.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring",
        uses = {QuestionIdMapper.class, QuestionnaireIdMapper.class, AccountIdMapper.class, CategoryIdMapper.class,
                WebhookIdMapper.class, TagIdMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuestionnaireResourcesMapper {

    @Mapping(target = "uuid", source = "id")
    QuestionnaireResources questionnaireToResources(Questionnaire questionnaire);

    Questionnaire questionnaireToModel(QuestionnaireResources questionnaireResources);

    Questionnaire questionnaireToModel(String id, QuestionnaireResources questionnaireResources);

    TagResources questionnaireTagToResources(QuestionnaireTag questionnaireTag);

    QuestionnaireTag questionnaireTagToModel(TagResources tagResources);

    @Mapping(target = "uuid", source = "id")
    CategoryResources categoryToResources(Category category);

    @Mapping(target = "id", source = "uuid")
    Category categoryToModel(CategoryResources categoryResources);

    @Mapping(target = "uuid", source = "id")
    QuestionnaireQuestionResources questionnaireQuestionToResources(QuestionnaireQuestion questionnaireQuestion);

    Question questionToModel(QuestionResources questionResources);

    Question questionToModel(String id, QuestionResources questionResources);

    @Mapping(target = "uuid", source = "id")
    QuestionResources questionToResources(Question question);

    Response responseToModel(ResponseResources responseResources);

    ResponseResources responseToResources(Response response);

    TagResources questionTagToResources(QuestionTag questionTag);

    QuestionTag questionTagToModel(TagResources tagResources);

    @Mapping(target = "uuid", source = "id")
    QuestionTagsResources questionTagsToResources(QuestionTags questionTags);

    @Mapping(target = "uuid", source = "id")
    TagResources tagToResources(Tag tag);

    Tag tagToModel(String id, TagResources tagResources);

    Tag tagToModel(TagResources tagResources);

    Iterable <TagResources> tagsToResources(Iterable <Tag> tags);

    UploadResources uploadToResources(Upload upload);

    Upload uploadToModel(UploadResources uploadResources);

    @Mapping(target = "uuid", source = "id")
    AccountResources userToResources(Account account);

    Account userToModel(AccountResources accountResources);

    @Mapping(target = "uuid", source = "id")
    WebHookResources webhookToResources(WebHook webHook);

    WebHook webhookToModel(WebHookResources webHookResources);

    WebHook webhookToModel(String id, WebHookResources webHookResources);


    Iterable <CategoryResources> categoriesToResources(Iterable <Category> categories);

    default Page <WebHookResources> webhookToResources(Page <WebHook> page) {
        return page.map(this::webhookToResources);
    }

    default Page <UploadResources> uploadToResources(Page <Upload> page) {
        return page.map(this::uploadToResources);
    }

    default Page <TagResources> tagToResources(Page <Tag> page) {
        return page.map(this::tagToResources);
    }

    default Page <QuestionTagsResources> questionTagsToResources(Page <QuestionTags> page) {
        return page.map(this::questionTagsToResources);
    }

    default Page <QuestionnaireResources> questionnaireToResources(Page <Questionnaire> page) {
        return page.map(this::questionnaireToResources);
    }

    default Page <QuestionnaireQuestionResources> questionnaireQuestionToResources(Page <QuestionnaireQuestion> page) {
        return page.map(this::questionnaireQuestionToResources);
    }

}
