package com.emu.apps.qcm.services;

import com.emu.apps.qcm.services.entity.epics.Epic;
import com.emu.apps.qcm.services.entity.questionnaires.Questionnaire;
import com.emu.apps.qcm.services.entity.questionnaires.QuestionnaireQuestion;
import com.emu.apps.qcm.services.entity.questions.Choice;
import com.emu.apps.qcm.services.entity.questions.Question;
import com.emu.apps.qcm.services.entity.questions.Response;
import com.emu.apps.qcm.services.entity.questions.Type;
import com.emu.apps.qcm.services.entity.tags.QuestionTag;
import com.emu.apps.qcm.services.entity.tags.QuestionnaireTagBuilder;
import com.emu.apps.qcm.services.entity.tags.Tag;
import com.emu.apps.qcm.services.repositories.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class FixtureService {

    public static final String QUESTION_QUESTION_1 = "a cool question";

    public static final String QUESTION_QUESTION_2 = "a cool question 2";

    public static final String RESPONSE_RESPONSE_1 = "a cool response 1";

    public static final String RESPONSE_RESPONSE_2 = "a cool response 2";

    public static final String TAG_LIBELLE_1 = "Tag1";

    public static final String TAG_LIBELLE_2 = "Tag2";

    public static final String TAG_LIBELLE_3 = "Tag3";

    public static final String TAG_LIBELLE_4 = "Tag45";

    public static final String CATEGORIE_LIBELLE = "Categ lib";

    public static final String QUESTIONNAIRE_TITLE = "Questionnaire";

    public static final String QUESTIONNAIRE_DESC = "Questionnaire desc";

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private QuestionTagRepository questionTagRepository;

    @Autowired
    private QuestionnaireRepository questionnaireJpaRepository;

    @Autowired
    private QuestionnaireQuestionRepository questionnaireQuestionRepository;

    @Autowired
    private EpicRepository epicRepository;

    @Autowired
    private ResponseRepository responseCrudRepository;

    @Autowired
    private QuestionnaireTagRepository questionnaireTagRepository;

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public FixtureService() {
    }

   @Transactional(readOnly = true)
   public Tag findTagbyLibelle(String Name){
        return tagRepository.findByLibelle(TAG_LIBELLE_4);
   }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Questionnaire createQuestionQuestionnaireTag() {

        responseCrudRepository.deleteAll();
        questionTagRepository.deleteAll();
        questionnaireTagRepository.deleteAll();
        questionnaireQuestionRepository.deleteAll();
        questionRepository.deleteAll();
        tagRepository.deleteAll();
        epicRepository.deleteAll();
        questionnaireJpaRepository.deleteAll();

        // category
        Epic epic = new Epic();
        epic.setLibelle(CATEGORIE_LIBELLE);
        epic = epicRepository.save(epic);

        //questionnaire
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setTitle(QUESTIONNAIRE_TITLE);
        questionnaire.setDescription(QUESTIONNAIRE_DESC);
        questionnaire.setEpic(epic);
        questionnaire = questionnaireJpaRepository.save(questionnaire);

        Response response = new Response();
        response.setNumber(1l);
        response.setResponse(RESPONSE_RESPONSE_1);
        responseCrudRepository.save(response);

        Response response2 = new Response();
        response2.setNumber(2l);
        response2.setResponse(RESPONSE_RESPONSE_2);
        responseCrudRepository.save(response2);

        //question 1
        Question question = new Question();
        question.setQuestion(QUESTION_QUESTION_1);
        question.setType(Type.FREE_TEXT);

        // question.setQuestionnaire(questionnaire);
        question.setResponses(Lists.newArrayList(response, response2));
        questionRepository.save(question);

        //question 2
        Question question2 = new Question();
        question2.setQuestion(QUESTION_QUESTION_2);
        question2.setType(Type.FREE_TEXT);

        questionRepository.save(question2);

        //questionTag
        Tag tag = tagRepository.save(new Tag(TAG_LIBELLE_1, false));
        Tag tag2 = tagRepository.save(new Tag(TAG_LIBELLE_2, false));

        questionTagRepository.save(new QuestionTag(question, tag));
        questionTagRepository.save(new QuestionTag(question, tag2));
        questionTagRepository.save(new QuestionTag(question2, tag));
        questionTagRepository.save(new QuestionTag(question2, tag2));



        questionnaireQuestionRepository.save(new QuestionnaireQuestion(questionnaire, question, 1L));

        questionnaireQuestionRepository.save(new QuestionnaireQuestion(questionnaire, question2, 2L));

        //questionnaireTag
        Tag tag4 = tagRepository.save(new Tag(TAG_LIBELLE_4, false));
        questionnaireTagRepository.save(new QuestionnaireTagBuilder().setQuestionnaire(questionnaire).setTag(tag4).createQuestionnaireTag());


        return questionnaire;

    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Question createQuestion() {

        questionnaireQuestionRepository.deleteAll();
        responseCrudRepository.deleteAll();
        questionTagRepository.deleteAll();
        questionnaireTagRepository.deleteAll();
        questionRepository.deleteAll();
        tagRepository.deleteAll();

        Choice choice = new Choice("message", 1L, true);
        Choice choice2 = new Choice("message", 2L, false);

        Response response = new Response();
        response.setNumber(1l);
        response.setResponse(RESPONSE_RESPONSE_1);
        response.setChoices(Sets.newHashSet(choice, choice2));
        responseCrudRepository.save(response);

        Response response2 = new Response();
        response2.setNumber(2l);
        response2.setResponse(RESPONSE_RESPONSE_2);
        responseCrudRepository.save(response2);

        //question 1
        Question question = new Question();
        question.setQuestion(QUESTION_QUESTION_1);
        question.setType(Type.FREE_TEXT);

        question.setResponses(Lists.newArrayList(response, response2));
        questionRepository.save(question);

        //question 2
        Question question2 = new Question();
        question2.setQuestion(QUESTION_QUESTION_2);
        question2.setType(Type.FREE_TEXT);

        questionRepository.save(question2);

        //questionTag
        Tag tag = tagRepository.save(new Tag(TAG_LIBELLE_1, false));
        Tag tag2 = tagRepository.save(new Tag(TAG_LIBELLE_2, false));
        Tag tag3 = tagRepository.save(new Tag(TAG_LIBELLE_3, false));


        questionTagRepository.save(new QuestionTag(question, tag));
        questionTagRepository.save(new QuestionTag(question, tag2));
        questionTagRepository.save(new QuestionTag(question2, tag));
        questionTagRepository.save(new QuestionTag(question2, tag3));

        return question;

    }

}
