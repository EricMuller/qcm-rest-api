package com.emu.apps.qcm.schedulers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;


@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private AtomicInteger counter = new AtomicInteger(0);

    private  final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 30000)
    public void reportCurrentTime() {
        log.info("The time is now {}", dateFormat.format(new Date()));
    }

    //@Scheduled(cron = "*/2 * * * * *")
    /*
    public void cronJob() {
        int jobId = counter.incrementAndGet();
        LOGGER.info("Job " + new Date() + ", jobId:  {}", jobId);
    }
    */
}