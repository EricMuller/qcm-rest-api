package com.emu.apps.qcm.webflux.services;


import com.emu.apps.qcm.webflux.model.questions.Question;
import reactor.core.publisher.Flux;

public interface ReactiveQuestionService {

   
    Flux <Question> findAll();


}
