package com.emu.apps.qcm.reporting;

import com.emu.apps.qcm.web.dtos.export.ExportDto;
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
public class ReportService {

    public ByteArrayOutputStream getReportStream(ExportDto exportDto, TypeReport typeReport) {

        InputStream in = ReportService.class.getResourceAsStream("/template_questionnaire.docx");

        try {

            IXDocReport report = XDocReportRegistry
                    .getRegistry()
                    .loadReport(in, TemplateEngineKind.Velocity);

            IContext context = report.createContext();
            context.put("questionnaire", exportDto.getQuestionnaire());
            context.put("questions", exportDto.getQuestions());

            ByteArrayOutputStream out = new ByteArrayOutputStream();

            if (TypeReport.PDF.equals(typeReport)) {
                ByteArrayOutputStream outDocx = new ByteArrayOutputStream();
                report.process(context, outDocx);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(outDocx.toByteArray());
                XWPFDocument document = new XWPFDocument(byteArrayInputStream);
                PdfOptions options = PdfOptions.create();
                PdfConverter.getInstance().convert(document, out, options);
            } else {
                report.process(context, out);
            }

            return out;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }


}
