package com.emu.common;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

class TestDate {

    @Test
    void name() throws ParseException {

        long l = new SimpleDateFormat("dd/MM/yyyy").parse("20/08/2020").getTime();
        //long l = new Date().getTime();
        System.out.println(l);


    }
}
