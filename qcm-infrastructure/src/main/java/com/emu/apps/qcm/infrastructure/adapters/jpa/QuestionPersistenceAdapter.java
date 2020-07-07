package com.emu.apps.qcm.infrastructure.adapters.jpa;

import com.emu.apps.qcm.infrastructure.adapters.jpa.builders.QuestionTagBuilder;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.category.Category;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.questionnaires.QuestionnaireQuestion;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.questions.Question;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.tags.Tag;
import com.emu.apps.qcm.infrastructure.adapters.jpa.repositories.*;
import com.emu.apps.qcm.infrastructure.adapters.jpa.specifications.QuestionSpecificationBuilder;
import com.emu.apps.qcm.infrastructure.ports.QuestionPersistencePort;
import com.emu.apps.qcm.mappers.QuestionMapper;
import com.emu.apps.qcm.mappers.UuidMapper;
import com.emu.apps.qcm.models.QuestionDto;
import com.emu.apps.qcm.models.QuestionTagDto;
import com.emu.apps.qcm.models.question.QuestionTagsDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.StreamSupport;

/**
 * Created by eric on 05/06/2017.
 */
@Service
@Transactional
public class QuestionPersistenceAdapter implements QuestionPersistencePort {

    private final QuestionRepository questionRepository;

    private final QuestionTagRepository questionTagRepository;

    private final QuestionnaireQuestionRepository questionnaireQuestionRepository;

    private QuestionMapper questionMapper;

    private TagRepository tagRepository;

    private CategoryRepository categoryRepository;

    private UuidMapper uuidMapper;

    @Autowired
    public QuestionPersistenceAdapter(QuestionRepository questionRepository, QuestionTagRepository questionTagRepository,
                                      QuestionnaireQuestionRepository questionnaireQuestionRepository,
                                      QuestionMapper questionMapper, TagRepository tagRepository,
                                      CategoryRepository categoryRepository, UuidMapper uuidMapper) {
        this.questionRepository = questionRepository;
        this.questionTagRepository = questionTagRepository;
        this.questionnaireQuestionRepository = questionnaireQuestionRepository;
        this.questionMapper = questionMapper;
        this.tagRepository = tagRepository;
        this.categoryRepository = categoryRepository;
        this.uuidMapper = uuidMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public QuestionDto findByUuid(String uuid) {
        return questionMapper.modelToDto(questionRepository.findByUuid(UUID.fromString(uuid)).orElse(null));

    }


    @Override
    public void deleteByUuid(String uuid) {
        questionRepository.deleteByUuid(UUID.fromString(uuid));
    }

    @Override
    public QuestionDto saveQuestion(QuestionDto questionDto, String principal) {

        Question question;
        Category category = null;
        UUID uuid = uuidMapper.getUuid(questionDto.getCategory());

        if (Objects.nonNull(uuid)) {
            category = categoryRepository.findByUuid(uuid);
        }

        if (StringUtils.isNotBlank(questionDto.getUuid())) {
            question = questionRepository.findByUuid(UUID.fromString(questionDto.getUuid())).orElse(null);
            question = questionMapper.dtoToModel(question, questionDto);
        } else {
            question = questionMapper.dtoToModel(questionDto);
        }

        question.setCategory(category);


        question = questionRepository.save(question);

        question = saveQuestionTags(question, questionDto.getQuestionTags(), principal);

        return questionMapper.modelToDto(question);

    }

//    @Override
//    public QuestionTag saveQuestionTag(QuestionTag questionTag) {
//        return questionTagCrudRepository.save(questionTag);
//    }

    @Override
    @Transactional(readOnly = true)
    public Page <QuestionTagsDto> findAllByPage(String[] questionnaireUuids, String[] tagUuids, Pageable pageable, String principal) {

        var questionSpecificationBuilder = new QuestionSpecificationBuilder();

        questionSpecificationBuilder.setPrincipal(principal);
        questionSpecificationBuilder.setQuestionnaireUuids(uuidMapper.toUUIDs(questionnaireUuids));
        questionSpecificationBuilder.setTagUuids(uuidMapper.toUUIDs(tagUuids));

        return questionMapper.pageToPageTagDto(questionRepository.findAll(questionSpecificationBuilder.build(), pageable));
    }




    @Override
    @Transactional(readOnly = true)
    public Iterable <QuestionnaireQuestion> findAllWithTagsAndResponseByQuestionnaireUuid(String questionnaireUuid) {

        return questionnaireQuestionRepository.findAllWithTagsAndResponseByQuestionnaireUuid(UUID.fromString(questionnaireUuid));

    }



    private Question saveQuestionTags(Question question, Iterable <QuestionTagDto> questionTags, String principal) {

        if (Objects.nonNull(question)) {
            question.getQuestionTags().clear();
            if (Objects.nonNull(questionTags)) {
                StreamSupport.stream(questionTags.spliterator(), false)
                        .forEach(questionTag -> {
                            Tag tag;
                            if (Objects.nonNull(questionTag.getUuid())) {
                                tag = tagRepository.findByUuid(UUID.fromString(questionTag.getUuid())).orElse(null);
                            } else {
                                tag = tagRepository.findByLibelle(questionTag.getLibelle(), principal);
                                if (Objects.isNull(tag)) {
                                    tag = tagRepository.save(new Tag(questionTag.getLibelle(), true));
                                }
                            }
                            if (Objects.nonNull(tag)) {
                                var newQuestionTag = new QuestionTagBuilder()
                                        .setQuestion(question)
                                        .setTag(tag)
                                        .build();
                                question.getQuestionTags().add(questionTagRepository.save(newQuestionTag));
                            }
                        });
            }
        }
        return question;
    }
}
