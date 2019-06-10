package com.emu.apps.qcm.schedulers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;


@Component
public class ScheduledTasks {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTasks.class);

    @Scheduled(fixedRate = 30000)
    public void reportCurrentTime() {
        LOGGER.info("The time is now {}", new SimpleDateFormat("HH:mm:ss").format(new Date()));
    }

}
