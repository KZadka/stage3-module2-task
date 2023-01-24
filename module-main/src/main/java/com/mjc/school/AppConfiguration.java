package com.mjc.school;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = {"com.mjc.school", "com.mjc.school.*"})
@EnableAspectJAutoProxy
public class AppConfiguration {

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

}
