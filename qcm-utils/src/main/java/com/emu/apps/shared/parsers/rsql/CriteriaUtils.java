package com.emu.apps.shared.parsers.rsql;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
public final class CriteriaUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(CriteriaUtils.class);

    private CriteriaUtils() {
    }

    private static boolean isEmpty(final byte[] data) {
        return IntStream.range(0, data.length).parallel().allMatch(i -> data[i] == 0);
    }

    public static Criteria[] toCriteria(String search) throws IOException {
        Criteria[] criteriaDtos = new Criteria[0];
        if (StringUtils.isNoneEmpty(search)) {
            byte[] bytes = Base64.getDecoder().decode(search);
            if (!isEmpty(bytes)) {
                try {
                    criteriaDtos = new ObjectMapper().readValue(bytes, Criteria[].class);
                } catch (JsonMappingException e) {
                    LOGGER.error(String.valueOf(bytes));
                    throw e;
                }

            }
        }
        return criteriaDtos;
    }

    public static Long[] getFieldIds(String attribute, Criteria[] criterias) {
        return Arrays.stream(criterias)
                .filter((criteria -> attribute.equals(criteria.getName())))
                .map(t -> Long.valueOf(t.getValue())).toArray(it -> new Long[it]);
    }

    public static Optional<String> getAttribute(String attribute, Criteria[] criterias) {
        return Arrays.stream(criterias).
                filter((filterDto -> attribute.equals(filterDto.getName())))
                .map(Criteria::getValue)
                .findFirst();
    }

}
