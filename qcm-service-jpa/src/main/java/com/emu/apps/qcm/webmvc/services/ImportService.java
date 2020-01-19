package com.emu.apps.qcm.webmvc.services;

import com.emu.apps.qcm.web.dtos.FileQuestionDto;

import java.security.Principal;

public interface ImportService {

    void createQuestionnaires(String name, FileQuestionDto[] fileQuestionDtos,Principal principal);
}
