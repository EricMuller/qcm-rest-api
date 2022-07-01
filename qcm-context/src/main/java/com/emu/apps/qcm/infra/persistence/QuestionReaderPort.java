package com.emu.apps.qcm.infra.persistence;


import com.emu.apps.qcm.domain.query.question.QuestionWithTagsOnly;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionReaderPort {


    Page <QuestionWithTagsOnly> findAllByPage(String[] questionnaireIds, String[] tagIds, Pageable pageable, String principal);


    Iterable <String> findAllStatusByPage(String principal, Pageable pageable);

}
