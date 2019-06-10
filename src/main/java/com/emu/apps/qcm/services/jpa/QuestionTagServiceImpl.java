package com.emu.apps.qcm.services.jpa;

import com.emu.apps.qcm.services.QuestionTagService;
import com.emu.apps.qcm.services.TagService;
import com.emu.apps.qcm.services.jpa.entity.questions.Question;
import com.emu.apps.qcm.services.jpa.entity.tags.QuestionTag;
import com.emu.apps.qcm.services.jpa.entity.tags.QuestionTagBuilder;
import com.emu.apps.qcm.services.jpa.entity.tags.Tag;
import com.emu.apps.qcm.services.jpa.repositories.QuestionRepository;
import com.emu.apps.qcm.services.jpa.repositories.QuestionTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional()
public class QuestionTagServiceImpl implements QuestionTagService {

    private final QuestionRepository questionRepository;

    private final QuestionTagRepository questionTagRepository;

    private final TagService tagService;

    @Autowired
    public QuestionTagServiceImpl(QuestionRepository questionRepository, QuestionTagRepository questionTagRepository, TagService tagService) {
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
        Optional <Question> questionOptional = questionRepository.findById(questionId);

        Question question = questionOptional.orElse(null);
        if (Objects.nonNull(question)) {
            question.getQuestionTags().clear();

            if (Objects.nonNull(questionTags)) {
                for (QuestionTag questionTag : questionTags) {
                    Tag tag;
                    if (Objects.nonNull(questionTag.getId().getTagId())) {
                        tag = tagService.findById(questionTag.getId().getTagId()).orElse(null);
                    } else {
                        tag = tagService.findOrCreateByLibelle(questionTag.getTag().getLibelle(), principal);
                    }
                    if (Objects.nonNull(tag)) {
                        QuestionTag newTag = new QuestionTagBuilder().setQuestion(question).setTag(tag).createQuestionnaireTag();
                        question.getQuestionTags().add(questionTagRepository.save(newTag));
                    }
                }
            }
        }
        return question;
    }
}
