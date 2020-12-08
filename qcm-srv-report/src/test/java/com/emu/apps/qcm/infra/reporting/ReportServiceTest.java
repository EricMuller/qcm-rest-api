package com.emu.apps.qcm.infra.reporting;

import com.emu.apps.qcm.domain.dtos.export.v1.CategoryExportDto;
import com.emu.apps.qcm.domain.dtos.export.v1.QuestionExportDto;
import com.emu.apps.qcm.domain.dtos.export.v1.QuestionnaireExportDto;
import com.emu.apps.qcm.domain.dtos.export.v1.ResponseExportDto;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.Status;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions.TypeQuestionEnum;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Arrays;

class ReportServiceTest {

    @Test
    void reportTest() throws IOException, XDocReportException {

        try (InputStream in = ReportServiceAdapter.class.getResourceAsStream("/template_questionnaire.docx")) {

            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in,
                    TemplateEngineKind.Velocity);


            QuestionnaireExportDto questionnaireExportDto = new QuestionnaireExportDto();
            questionnaireExportDto.setTitle("Java Thread");
            questionnaireExportDto.setWebsite("http://qcm.webmarks.net");

            questionnaireExportDto.setStatus(Status.DRAFT.name());

            CategoryExportDto categoryExportDto = new CategoryExportDto();
            categoryExportDto.setLibelle("Java");
            questionnaireExportDto.setCategory(categoryExportDto);


            QuestionExportDto questionExportDto = new QuestionExportDto();
            questionExportDto.setQuestionText("What is a Thread in Java?");
            questionExportDto.setType(TypeQuestionEnum.FREE_TEXT.name());
            questionExportDto.setPosition(1L);

            String response = "A thread in Java is a lightweight process that runs within another" +
                    "process or thread." +
                    "        It is an independent path of execution in an application. JVM gives" +
                    "each thread its own method-call stack." +
                    "        When we start JVM, Java starts one thread.  This thread calls the" +
                    "main method of the class passed in argument to java call";

            ResponseExportDto responseExportDto = new ResponseExportDto();
            responseExportDto.setResponseText(response);
            responseExportDto.setGood(true);


            questionExportDto.setResponses(Arrays.asList(responseExportDto));

            IContext context = report.createContext();
            context.put("questionnaire", questionnaireExportDto);
            context.put("questions", Arrays.asList(questionExportDto, questionExportDto));

            try (OutputStream out = new FileOutputStream(new File("./target/questionnaire_out.docx"))) {
                report.process(context, out);
                Assertions.assertNotNull(out);
            }

        }

    }
}
