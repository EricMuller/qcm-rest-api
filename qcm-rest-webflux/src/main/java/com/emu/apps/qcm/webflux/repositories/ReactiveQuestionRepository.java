package com.emu.apps.qcm.webflux.repositories;


import com.emu.apps.qcm.webflux.model.questions.Question;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository
public interface ReactiveQuestionRepository extends R2dbcRepository <Question, Long> {


}
