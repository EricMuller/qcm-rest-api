package com.emu.apps.qcm.infra.reporting;

import com.emu.apps.qcm.domain.models.export.v1.Export;
import com.emu.apps.qcm.infra.reporting.converters.Converter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import static com.emu.apps.qcm.infra.reporting.ContextBuilder.createContext;

/**
 *
 */
@Service
@Slf4j
public class ReportServiceAdapter implements ReportServicePort {

    private final Converter pdfConverter;

    private final Converter wordConverter;

    private final Converter jsonConverter;

    public ReportServiceAdapter(@Qualifier("XdocPdfConverter") Converter pdfConverter, @Qualifier("XdocWordConverter") Converter wordConverter
            , @Qualifier("JacksonConverter") Converter jsonConverter) {
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
