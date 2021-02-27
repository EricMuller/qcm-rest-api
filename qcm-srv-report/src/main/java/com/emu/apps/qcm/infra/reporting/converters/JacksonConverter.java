package com.emu.apps.qcm.infra.reporting.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

/**
 *
 */
@Service(value = "JacksonConverter")
@Slf4j
public class JacksonConverter implements Converter {

    /**
     * objectMapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS,true);
     *
     * @param object
     * @return OupuStream
     */
    @Override
    public byte[] convert(Object object) {
        try {
            ObjectMapper mapper = new ObjectMapper()
                    .findAndRegisterModules()
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    .configure(SerializationFeature.INDENT_OUTPUT, true);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            mapper.writeValue(byteArrayOutputStream, object);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }


}
