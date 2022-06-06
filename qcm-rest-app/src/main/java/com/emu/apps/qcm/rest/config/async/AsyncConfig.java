package com.emu.apps.qcm.rest.config.async;

import com.emu.apps.qcm.rest.config.async.AsyncExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.concurrent.DelegatingSecurityContextRunnable;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@EnableAsync
public class AsyncConfig extends AsyncConfigurerSupport {

    private final AsyncExceptionHandler exceptionHandler;

    @Bean(name = "asyncExecutor")
    public ThreadPoolTaskExecutor getAsyncExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(2);
        threadPoolTaskExecutor.setMaxPoolSize(5);
        threadPoolTaskExecutor.setQueueCapacity(100);
//        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        threadPoolTaskExecutor.initialize();
        threadPoolTaskExecutor.setThreadNamePrefix("async-task-");
        //By doing so, Spring will use the current SecurityContext inside each @Async call.
        threadPoolTaskExecutor.setTaskDecorator(runnable -> new DelegatingSecurityContextRunnable(runnable));
        return threadPoolTaskExecutor;
    }

    @Bean("taskExecutor")
    Executor taskExecutor() {
        return Executors.newFixedThreadPool(10);
    }

    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return exceptionHandler;
    }

}
