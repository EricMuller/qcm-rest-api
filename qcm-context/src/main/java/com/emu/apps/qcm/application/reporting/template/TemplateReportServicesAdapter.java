package com.emu.apps.qcm.application.reporting.template;


import com.emu.apps.shared.converters.Converter;
import com.emu.apps.qcm.application.reporting.dtos.Export;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import static com.emu.apps.qcm.application.reporting.template.ContextBuilder.createContext;

/**
 *
 */
@Service
@Slf4j
public class TemplateReportServicesAdapter implements TemplateReportServices {

    private final Converter pdfConverter;

    private final Converter wordConverter;

    private final Converter jsonConverter;

    public TemplateReportServicesAdapter(@Qualifier("XdocPdfConverter") Converter pdfConverter,
                                         @Qualifier("XdocWordConverter") Converter wordConverter,
                                         @Qualifier("JacksonConverter") Converter jsonConverter) {
        this.pdfConverter = pdfConverter;
        this.wordConverter = wordConverter;
        this.jsonConverter = jsonConverter;
    }

    @Override
    public byte[] convertAsStream(Export exportDataDto, ReportTemplate reportTemplate, FileFormat fileFormat) {

        switch (fileFormat) {
            case PDF:
                return pdfConverter.convert(createContext(exportDataDto), reportTemplate.getFileName());
            case DOCX:
                return wordConverter.convert(createContext(exportDataDto), reportTemplate.getFileName());
            case JSON:
                return jsonConverter.convert(exportDataDto);
            default:
                throw new IllegalArgumentException(fileFormat.name());
        }

    }

}
