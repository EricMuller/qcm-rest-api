package com.emu.apps.qcm.rest.config.cache;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean("PageableKeyGenerator")
    public KeyGenerator keyGenerator() {
        return new PageableKeyGenerator();
    }

    public class PageableKeyGenerator implements KeyGenerator {

        public Object generate(Object target, Method method, Object... params) {
            String code = "UNIQUE_CODE"; // implements logic from params
            return code;
        }

    }

}



