package com.emu.apps.qcm.webmvc.services.config;

import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class CurrentTimeDateTimeService implements DateTimeService {
 
    @Override
    public ZonedDateTime getCurrentDateAndTime() {
        return ZonedDateTime.now();
    }
}
