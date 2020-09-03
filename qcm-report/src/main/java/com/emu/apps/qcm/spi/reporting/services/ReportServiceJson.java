package com.emu.apps.qcm.spi.reporting.services;

import com.emu.apps.qcm.api.dtos.export.v1.ExportDto;
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

    /**
     *
     * objectMapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS,true);
     *
     * @param exportDataDto
     * @return OupuStream
     */
    @Override
    public ByteArrayOutputStream getReportStream(ExportDto exportDataDto) {
        try {
            ObjectMapper mapper = new ObjectMapper()
                    .findAndRegisterModules()
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    .configure(SerializationFeature.INDENT_OUTPUT, true);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            mapper.writeValue(byteArrayOutputStream, exportDataDto);
            return byteArrayOutputStream;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }


}
