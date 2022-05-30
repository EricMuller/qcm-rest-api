package com.emu.apps.qcm.application.export.converters.json;


import com.emu.apps.shared.converters.JacksonConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
@Slf4j
public class ObjectToJsonConverterAdapter implements ObjectToJsonConverter {

    private final JacksonConverter jacksonConverter;

    public ObjectToJsonConverterAdapter(JacksonConverter jsonConverter) {
        this.jacksonConverter = jsonConverter;
    }

    @Override
    public byte[] convertToByteArray(Object object) {

        return jacksonConverter.convertToByteArray(object);

    }

}
