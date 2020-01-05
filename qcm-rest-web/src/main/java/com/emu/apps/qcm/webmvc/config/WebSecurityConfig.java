package com.emu.apps.qcm.webmvc.config;


import com.emu.apps.qcm.webmvc.rest.QcmApi;
import com.emu.apps.users.webmvc.rest.UserApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@Configuration
@EnableWebSecurity
@Profile("webmvc")
class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new NullAuthenticatedSessionStrategy();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and()
                .csrf().disable().
                sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .oauth2Client(Customizer.withDefaults())
                .oauth2ResourceServer().jwt().jwtAuthenticationConverter(new GrantedAuthoritiesConverter()).and()
                .and()
                .authorizeRequests()
                .antMatchers(QcmApi.API_V1 + "/**").authenticated()
                .antMatchers(UserApi.API_V1 + "/**").authenticated()
                .antMatchers("/info/**").permitAll()
                .anyRequest().permitAll();
    }

}
