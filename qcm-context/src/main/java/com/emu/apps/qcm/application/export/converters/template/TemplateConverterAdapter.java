package com.emu.apps.qcm.application.export.converters.template;


import com.emu.apps.qcm.application.export.ExportFormat;
import com.emu.apps.shared.converters.Converter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

import static java.util.Objects.nonNull;

/**
 *
 */
@Service
@Slf4j
public class TemplateConverterAdapter implements TemplateConverter {

    private final Map <ExportFormat, Converter> converters;

    public TemplateConverterAdapter(@Qualifier(Converter.PDF) Converter pdfConverter,
                                    @Qualifier(Converter.WORD) Converter wordConverter) {
        this.converters = Map.of(ExportFormat.DOCX, wordConverter, ExportFormat.PDF, pdfConverter);
    }

    @Override
    public byte[] convertToByteArray(Map <String, Object> context, Template template, ExportFormat exportFormat) {

        final Converter converter = converters.containsKey(exportFormat) ? converters.get(exportFormat) : null;

        if (nonNull(converter)) {
            return converter.convert(context, template.getFileName());
        }

        throw new IllegalArgumentException(exportFormat.name());

    }

}
