package com.emu.apps.qcm.reporting.services;

import com.emu.apps.qcm.dtos.export.ExportDataDto;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 *
 */
@Service
@Slf4j
public class ReportServicePdf implements  ReportService {

    @Override
    public ByteArrayOutputStream getReportStream(ExportDataDto exportDataDto) {

        try {

            InputStream in = ReportServiceWord.class.getResourceAsStream("/" + TemplateNames.QUESTIONNAIRE.getName());

            IXDocReport report = XDocReportRegistry
                    .getRegistry()
                    .loadReport(in, TemplateEngineKind.Velocity);

            IContext context = report.createContext();
            context.put("questionnaire", exportDataDto.getQuestionnaire());
            context.put("questions", exportDataDto.getQuestions());

            ByteArrayOutputStream outDocx = new ByteArrayOutputStream();
            report.process(context, outDocx);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(outDocx.toByteArray());
            XWPFDocument document = new XWPFDocument(byteArrayInputStream);
            PdfOptions options = PdfOptions.create();

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfConverter.getInstance().convert(document, out, options);

            return out;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

}
