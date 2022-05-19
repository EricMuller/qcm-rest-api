package com.emu.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TestJackson {

    private class Request {
        ZonedDateTime zonedDateTime;

        public ZonedDateTime getZonedDateTime() {
            return zonedDateTime;
        }
    }
    @Test
    void name() throws JsonProcessingException {

        ObjectMapper objectMapper = JsonMapper.builder()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true)
              //  .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .addModule(new JavaTimeModule()).build();


        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
        objectMapper.setDateFormat(df);

        LocalDateTime localDateTime = LocalDateTime.of(2000, Month.JANUARY, 1, 0, 0, 0);

        ZoneId vnZoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        // Add time zone information to LocalDateTime.
        Request request = new Request();
        request.zonedDateTime = ZonedDateTime.of(localDateTime, vnZoneId);

        String carAsString = objectMapper.writeValueAsString(request);

        System.out.println(carAsString);
    }
}
