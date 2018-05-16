package com.emu.apps.qcm.web.rest.utils;

import com.emu.apps.qcm.web.rest.dtos.FilterDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;
import java.util.stream.IntStream;

@Service
final public class StringToFilter {

    private StringToFilter() {
    }

    public static boolean isEmpty(final byte[] data) {
        return IntStream.range(0, data.length).parallel().allMatch(i -> data[i] == 0);
    }

    public FilterDto[] getFilterDtos(String filterString) throws IOException {
        FilterDto[] filterDtos = new FilterDto[0];
        if (StringUtils.isNoneEmpty(filterString)) {
            byte[] bytes = Base64.getDecoder().decode(filterString);
            if (!isEmpty(bytes)) {
                filterDtos = new ObjectMapper().readValue(bytes, FilterDto[].class);
            }
        }
        return filterDtos;
    }
}
