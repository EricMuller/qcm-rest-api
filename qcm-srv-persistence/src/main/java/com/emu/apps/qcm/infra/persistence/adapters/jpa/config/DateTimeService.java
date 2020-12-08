package com.emu.apps.qcm.infra.persistence.adapters.jpa.config;

import java.time.ZonedDateTime;

public interface DateTimeService {
 
    ZonedDateTime getCurrentDateAndTime();
}
