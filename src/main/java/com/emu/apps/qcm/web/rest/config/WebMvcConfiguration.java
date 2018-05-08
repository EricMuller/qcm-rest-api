package com.emu.apps.qcm.web.rest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // test cache
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/").setCachePeriod(3600 * 24);

    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // forward requests to index.html
        registry.addViewController("/static/").setViewName(
                "forward:/static/index.html");
    }
}
