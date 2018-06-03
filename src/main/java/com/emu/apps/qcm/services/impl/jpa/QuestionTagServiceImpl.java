package com.emu.apps.qcm.services.impl.jpa;

import com.emu.apps.qcm.services.QuestionTagService;
import com.emu.apps.qcm.services.TagService;
import com.emu.apps.qcm.services.entity.questions.Question;
import com.emu.apps.qcm.services.entity.tags.QuestionTag;
import com.emu.apps.qcm.services.entity.tags.QuestionTagBuilder;
import com.emu.apps.qcm.services.entity.tags.Tag;
import com.emu.apps.qcm.services.repositories.QuestionRepository;
import com.emu.apps.qcm.services.repositories.QuestionTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional()
public class QuestionTagServiceImpl implements QuestionTagService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionTagRepository questionTagRepository;

    @Autowired
    private TagService tagService;

    @Override
    public QuestionTag saveQuestionTag(QuestionTag questionTag) {
        return questionTagRepository.save(questionTag);
    }

    @Override
    // todo: to refacto with abstract entity
    public Question saveQuestionTags(long questionId, Iterable<QuestionTag> questionTags) {
        Question question = questionRepository.findOne(questionId);

        question.getQuestionTags().clear();

        if (Objects.nonNull(questionTags)) {
            for (QuestionTag questionTag : questionTags) {
                Tag tag ;
                if (Objects.nonNull(questionTag.getId().getTagId())) {
                    tag = tagService.findById(questionTag.getId().getTagId());
                } else {
                    tag = tagService.findOrCreateByLibelle(questionTag.getTag().getLibelle());
                }
                if (tag != null) {
                    QuestionTag newTag = new QuestionTagBuilder().setQuestion(question).setTag(tag).createQuestionnaireTag();
                    question.getQuestionTags().add(questionTagRepository.save(newTag));
                }
            }
        }
        return question;
    }
}
