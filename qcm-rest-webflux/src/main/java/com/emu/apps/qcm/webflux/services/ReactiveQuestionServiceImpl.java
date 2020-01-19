package com.emu.apps.qcm.webflux.services;


import com.emu.apps.qcm.webflux.model.questions.Question;
import com.emu.apps.qcm.webflux.repositories.ReactiveQuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

/**
 * Created by eric on 14/06/2017.
 */
@Service
@Transactional()
public class ReactiveQuestionServiceImpl implements ReactiveQuestionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReactiveQuestionServiceImpl.class);

    private final ReactiveQuestionRepository reactiveQuestionRepository;

    @Autowired
    public ReactiveQuestionServiceImpl(ReactiveQuestionRepository reactiveQuestionRepository) {
        this.reactiveQuestionRepository = reactiveQuestionRepository;
    }

    @Override
    public Flux <Question> findAll() {
        return reactiveQuestionRepository.findAll();
    }

}
