package com.nuce.account.config;


import com.nuce.account.aop.ExceptionAspect;
import com.nuce.account.aop.LoggingAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingConfiguration {
    @Bean
    public LoggingAspect loggingAspect(){
        return new LoggingAspect();
    }
}
