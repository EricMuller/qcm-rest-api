package com.emu.apps.qcm.rest.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@TestConfiguration
@EnableWebSecurity
@Order(0)
public class WebSecurityTestConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf().disable();
        httpSecurity.authorizeRequests()
                .antMatchers("/")
                .hasRole("USER")
                .and()
                .httpBasic();
        httpSecurity.authorizeRequests()
                .anyRequest().authenticated();

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.inMemoryAuthentication()
                .withUser(H2TestProfileJPAConfig.USER_TEST)
                .password("{noop}" + H2TestProfileJPAConfig.USER_PASSWORD)
                .roles("USER");
    }


}
