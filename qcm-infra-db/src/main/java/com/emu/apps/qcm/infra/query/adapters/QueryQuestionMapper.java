package com.emu.apps.qcm.infra.query.adapters;


import org.apache.commons.beanutils.DynaBean;
//import org.apache.ibatis.annotations.Select;
//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Select;

import java.util.List;

//@Mapper
public interface QueryQuestionMapper {

//    @Select("select question from Question")
    List <DynaBean> findAllQuestions();

}
