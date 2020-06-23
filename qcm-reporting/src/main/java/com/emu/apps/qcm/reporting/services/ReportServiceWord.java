package com.emu.apps.qcm.reporting.services;

import com.emu.apps.qcm.dtos.export.ExportDataDto;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 *
 */
@Service
@Slf4j
public class ReportServiceWord implements  ReportService {

    @Override
    public ByteArrayOutputStream getReportStream(ExportDataDto exportDataDto) {

        try {
            InputStream in = ReportServiceWord.class.getResourceAsStream("/" + TemplateNames.QUESTIONNAIRE.getName());

            IXDocReport report = XDocReportRegistry
                    .getRegistry()
                    .loadReport(in, TemplateEngineKind.Velocity);
            IContext context = report.createContext();
            context.put("questions", exportDataDto.getQuestions());
            context.put("questionnaire", exportDataDto.getQuestionnaire());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            report.process(context, out);
            return out;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }


}
