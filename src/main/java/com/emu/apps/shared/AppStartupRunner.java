package com.emu.apps.shared;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppStartupRunner implements ApplicationRunner {
    private static final Logger LOG =  LoggerFactory.getLogger(AppStartupRunner.class);

    @Override
    public void run(ApplicationArguments args)  {
        LOG.warn("Application started with option names : {}",
          args.getOptionNames());
    }
}