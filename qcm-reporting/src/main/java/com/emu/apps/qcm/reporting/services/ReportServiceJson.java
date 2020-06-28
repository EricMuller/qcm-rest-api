package com.emu.apps.qcm.reporting.services;

import com.emu.apps.qcm.dtos.export.v1.ExportDataDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

/**
 *
 */
@Service
@Slf4j
public class ReportServiceJson implements ReportService {

    @Override
    public ByteArrayOutputStream getReportStream(ExportDataDto exportDataDto) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            objectMapper.writeValue(byteArrayOutputStream, exportDataDto);
            return byteArrayOutputStream;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }


}
