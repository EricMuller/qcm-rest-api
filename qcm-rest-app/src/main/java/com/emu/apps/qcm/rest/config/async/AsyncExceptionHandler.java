package com.emu.apps.qcm.rest.config.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Component
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {

        LOGGER.error("Async UnCaughtException handled with Error: [{}], MethodName: [{}], Class: [{}]",
                throwable.getStackTrace(), method.getExceptionTypes(), objects.getClass());

    }
}
