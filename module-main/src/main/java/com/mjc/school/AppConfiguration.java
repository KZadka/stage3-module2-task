package com.mjc.school;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = {"com.mjc.school",
        "com.mjc.school.repository",
        "com.mjc.school.service",
        "com.mjc.school.controller"})
@EnableAspectJAutoProxy
public class AppConfiguration {

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

}
