package com.emu.apps.qcm.rest.controllers.mappers;

import com.emu.apps.qcm.domain.model.Category;
import com.emu.apps.qcm.domain.model.question.Question;
import com.emu.apps.qcm.domain.model.question.QuestionTag;
import com.emu.apps.qcm.domain.model.question.QuestionTags;
import com.emu.apps.qcm.domain.model.question.Response;
import com.emu.apps.qcm.domain.model.questionnaire.Questionnaire;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireQuestion;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireTag;
import com.emu.apps.qcm.domain.model.tag.Tag;
import com.emu.apps.qcm.domain.model.upload.Upload;
import com.emu.apps.qcm.domain.model.user.User;
import com.emu.apps.qcm.domain.model.webhook.WebHook;
import com.emu.apps.qcm.rest.controllers.resources.*;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuestionnaireResourcesMapper {

    QuestionnaireResources questionnaireToResources(Questionnaire questionnaire);

    Questionnaire questionnaireToModel(QuestionnaireResources questionnaireResources);

    QuestionnaireTagResources questionnaireTagToResources(QuestionnaireTag questionnaireTag);

    QuestionnaireTag questionnaireTagToModel(QuestionnaireTagResources questionnaireTagResources);

    CategoryResources categoryToResources(Category category);

    Category categoryToModel(CategoryResources categoryResources);

    QuestionnaireQuestionResources questionnaireQuestionToResources(QuestionnaireQuestion questionnaireQuestion);

    Question questionToModel(QuestionResources questionResources);

    QuestionResources questionToResources(Question question);

    Response responseToModel(ResponseResources responseResources);

    ResponseResources responseToResources(Response response);

    QuestionTagResources questionTagToResources(QuestionTag questionnaireTag);

    QuestionTag questionTagToModel(QuestionTagResources questionTagResources);

    QuestionTagsResources questionTagsToResources(QuestionTags questionTags);

    TagResources tagToResources(Tag tag);

    Tag tagToModel(TagResources tagResources);

    Iterable <TagResources> tagsToResources(Iterable <Tag> tags);

    UploadResources uploadToResources(Upload upload);

    Upload uploadToModel(UploadResources uploadResources);

    UserResources userToResources(User user);

    User userToModel(UserResources userResources);

    WebHookResources webhookToResources(WebHook webHook);

    WebHook webhookToModel(WebHookResources webHookResources);


    Iterable <CategoryResources> categoriesToResources(Iterable <Category> categories) ;

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
