package com.emu.apps.qcm.reporting;

import com.emu.apps.qcm.domain.entity.questions.TypeQuestion;
import com.emu.apps.qcm.web.dtos.export.CategoryExportDto;
import com.emu.apps.qcm.web.dtos.export.QuestionExportDto;
import com.emu.apps.qcm.web.dtos.export.QuestionnaireExportDto;
import com.emu.apps.qcm.web.dtos.export.ResponseExportDto;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.core.utils.Assert;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Arrays;

class ReportServiceTest {

    @Test
    void name() throws IOException, XDocReportException {

        InputStream in = ReportService.class.getResourceAsStream("/template_questionnaire.docx");

        IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in,
                TemplateEngineKind.Velocity);


        QuestionnaireExportDto questionnaireExportDto = new QuestionnaireExportDto();
        questionnaireExportDto.setTitle("Java Thread");
        questionnaireExportDto.setWebsite("http://qcm.webmarks.net");

        CategoryExportDto categoryExportDto = new CategoryExportDto();
        categoryExportDto.setLibelle("Java");
        questionnaireExportDto.setCategory(categoryExportDto);


        QuestionExportDto questionExportDto = new QuestionExportDto();
        questionExportDto.setQuestion("What is a Thread in Java?");
        questionExportDto.setType(TypeQuestion.FREE_TEXT.name());
        questionExportDto.setPosition(1L);

        String response = "A thread in Java is a lightweight process that runs within another"+
        "process or thread."+
        "        It is an independent path of execution in an application. JVM gives"+
        "each thread its own method-call stack."+
        "        When we start JVM, Java starts one thread.  This thread calls the"+
        "main method of the class passed in argument to java call";

        ResponseExportDto responseExportDto = new ResponseExportDto();
        responseExportDto.setResponse(response);
        responseExportDto.setGood(true);


        questionExportDto.setResponses(Arrays.asList(responseExportDto));

        IContext context = report.createContext();
        context.put("questionnaire", questionnaireExportDto);
        context.put("questions", Arrays.asList(questionExportDto,questionExportDto));

        OutputStream out = new FileOutputStream(new File("questionnaire_out.docx"));
        report.process(context, out);

        Assert.notNull(out, "Erreur");

    }
}
