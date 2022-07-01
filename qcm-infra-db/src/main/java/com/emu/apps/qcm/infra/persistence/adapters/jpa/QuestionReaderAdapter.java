package com.emu.apps.qcm.infra.persistence.adapters.jpa;

import com.emu.apps.qcm.domain.query.question.QuestionWithTagsOnly;
import com.emu.apps.qcm.infra.persistence.QuestionReaderPort;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions.QuestionEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.mappers.QuestionQueryMapper;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.mappers.UuidMapper;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class QuestionReaderAdapter implements QuestionReaderPort {

    private final QuestionRepository questionRepository;

    private final QuestionQueryMapper questionQueryMapper;

    private final UuidMapper uuidMapper;


    @Autowired
    public QuestionReaderAdapter(QuestionRepository questionRepository, QuestionQueryMapper questionQueryMapper, UuidMapper uuidMapper) {
        this.questionRepository = questionRepository;
        this.questionQueryMapper = questionQueryMapper;
        this.uuidMapper = uuidMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Page <QuestionWithTagsOnly> findAllByPage(String[] questionnaireIds, String[] tagIds, Pageable pageable, String principal) {

        var questionSpecificationBuilder = new QuestionEntity.SpecificationBuilder(principal);

        questionSpecificationBuilder.setQuestionnaireUuids(uuidMapper.toUUIDs(questionnaireIds));
        questionSpecificationBuilder.setTagUuids(uuidMapper.toUUIDs(tagIds));

        return questionQueryMapper.pageEntityToQuestionWithTagOnly(questionRepository.findAll(questionSpecificationBuilder.build(), pageable));

    }

    @Override
    @Transactional(readOnly = true)
    public Iterable <String> findAllStatusByPage(String principal, Pageable pageable) {
        return StreamSupport.stream(questionRepository.findAllStatusByCreatedBy(principal, pageable)
                        .spliterator(), false)
                .collect(Collectors.toList());
    }

}
