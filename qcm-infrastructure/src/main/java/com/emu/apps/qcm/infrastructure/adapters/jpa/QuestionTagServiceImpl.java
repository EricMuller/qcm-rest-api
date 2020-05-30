package com.emu.apps.qcm.infrastructure.adapters.jpa;

import com.emu.apps.qcm.infrastructure.ports.QuestionTagDOService;
import com.emu.apps.qcm.infrastructure.ports.TagDOService;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.questions.Question;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.tags.QuestionTag;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.tags.Tag;
import com.emu.apps.qcm.infrastructure.adapters.jpa.builders.QuestionTagBuilder;
import com.emu.apps.qcm.infrastructure.adapters.jpa.repositories.QuestionRepository;
import com.emu.apps.qcm.infrastructure.adapters.jpa.repositories.QuestionTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Objects;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class QuestionTagServiceImpl implements QuestionTagDOService {

    private final QuestionRepository questionRepository;

    private final QuestionTagRepository questionTagRepository;

    private final TagDOService tagService;

    @Autowired
    public QuestionTagServiceImpl(QuestionRepository questionRepository, QuestionTagRepository questionTagRepository, TagDOService tagService) {
        this.questionRepository = questionRepository;
        this.questionTagRepository = questionTagRepository;
        this.tagService = tagService;
    }

    @Override
    public QuestionTag saveQuestionTag(QuestionTag questionTag) {
        return questionTagRepository.save(questionTag);
    }

    @Override
    public Question saveQuestionTags(long questionId, Iterable <QuestionTag> questionTags, Principal principal) {

        final var question = questionRepository.findById(questionId).orElse(null);

        if (Objects.nonNull(question)) {
            question.getQuestionTags().clear();
            if (Objects.nonNull(questionTags)) {
                StreamSupport.stream(questionTags.spliterator(), false)
                        .forEach(questionTag -> {
                            Tag tag;
                            if (Objects.nonNull(questionTag.getId().getTagId())) {
                                tag = tagService.findById(questionTag.getId().getTagId()).orElse(null);
                            } else {
                                tag = tagService.findOrCreateByLibelle(questionTag.getTag().getLibelle(), principal);
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
