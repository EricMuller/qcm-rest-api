package com.emu.apps.qcm.web.rest;

import org.junit.Test;

import java.io.IOException;
import java.util.Base64;

public class Decode64Test {

    @Test
    public void test() throws IOException {

        String encoded = "W3sidmFsdWUiOiIxIiwidHlwZSI6InRhZyJ9LHsidmFsdWUiOiIyIiwidHlwZSI6InRhZyJ9XQ==";

        byte[] message = Base64.getDecoder().decode(encoded);

        System.out.println(new String(message));
//
//        final FilterDto[] person =
//                new ObjectMapper().readValue(message, FilterDto[].class);
//
//        Arrays.stream(person).forEach((p) -> {
//            System.out.println(p.getValue());
//        });

    }
}
