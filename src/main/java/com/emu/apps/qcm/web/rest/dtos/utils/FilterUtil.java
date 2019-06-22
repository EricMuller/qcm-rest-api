package com.emu.apps.qcm.web.rest.dtos.utils;

import com.emu.apps.qcm.web.rest.dtos.FilterDto;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;
import java.util.stream.IntStream;

@Service
public final class FilterUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilterUtil.class);

    private FilterUtil() {
    }

    private boolean isEmpty(final byte[] data) {
        return IntStream.range(0, data.length).parallel().allMatch(i -> data[i] == 0);
    }

    public FilterDto[] stringToFilterDtos(String filterString) throws IOException {
        FilterDto[] filterDtos = new FilterDto[0];
        if (StringUtils.isNoneEmpty(filterString)) {
            byte[] bytes = Base64.getDecoder().decode(filterString);
            if (!isEmpty(bytes)) {
                try {
                    filterDtos = new ObjectMapper().readValue(bytes, FilterDto[].class);
                } catch (JsonMappingException e) {
                    LOGGER.error(String.valueOf(bytes));
                    throw e;
                }

            }
        }
        return filterDtos;
    }
}
