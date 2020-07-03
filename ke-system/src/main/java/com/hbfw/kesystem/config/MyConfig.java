package com.hbfw.kesystem.config;

import com.hbfw.kesystem.resolver.ParamLocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;

@Configuration
public class MyConfig {

    @Bean
    public LocaleResolver localeResolver(){
        return new ParamLocaleResolver();
    }
}
