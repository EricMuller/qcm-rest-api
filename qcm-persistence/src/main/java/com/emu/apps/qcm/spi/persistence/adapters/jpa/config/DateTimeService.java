package com.emu.apps.qcm.spi.persistence.adapters.jpa.config;

import java.time.ZonedDateTime;

public interface DateTimeService {
 
    ZonedDateTime getCurrentDateAndTime();
}
