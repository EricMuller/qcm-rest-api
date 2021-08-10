package com.emu.apps.shared.converters;

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
import java.util.Map;

/**
 *
 */
@Service(value = "XdocPdfConverter")
@Slf4j
public class XdocPdfConverter implements Converter {

    @Override
    public byte[] convert(Map <String, Object> map, String template) {

        try {

            try (InputStream in = XdocWordConverter.class.getResourceAsStream("/" + template)) {

                IXDocReport report = XDocReportRegistry
                        .getRegistry()
                        .loadReport(in, TemplateEngineKind.Velocity);

                IContext context = report.createContext(map);
                ByteArrayOutputStream outDocx = new ByteArrayOutputStream();
                report.process(context, outDocx);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(outDocx.toByteArray());
                XWPFDocument document = new XWPFDocument(byteArrayInputStream);
                PdfOptions options = PdfOptions.create();

                ByteArrayOutputStream out = new ByteArrayOutputStream();
                PdfConverter.getInstance().convert(document, out, options);

                return out.toByteArray();
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

}
