package com.emu.tests;

import org.junit.jupiter.api.BeforeAll;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/*
-Duser.timezone="Africa/Djibouti"
 */
class CalendarTest {

    @BeforeAll
    static void name() {
        TimeZone.setDefault(TimeZone.getTimeZone("US/Eastern"));
    }

    @org.junit.jupiter.api.Test
    void test() {

        createCalendar(TimeZone.getDefault());

        createCalendar(TimeZone.getTimeZone("US/Eastern"));
        createCalendar(TimeZone.getTimeZone("GMT"));
        createCalendar(TimeZone.getTimeZone("Europe/Paris"));


    }

    private Calendar createCalendar(TimeZone timeZone) {
        final GregorianCalendar calendar = new GregorianCalendar(timeZone);

        calendar.set(Calendar.YEAR, 2021);
        calendar.set(Calendar.MONTH, 4);
        calendar.set(Calendar.DAY_OF_MONTH, 21);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        System.out.println("-------------------");
        System.out.println(timeZone.getDisplayName());
        System.out.println(calendar.getTime());
        System.out.println(calendar.getTimeInMillis());
        return calendar;
    }
}
