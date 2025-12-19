package com.fincomun.validadorcurp.bean;

import com.fincomun.validadorcurp.dto.properties.CurpProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class PropertiesBeans {

    @Bean
    @ConfigurationProperties(prefix = "curp")
    public CurpProperties curpProperties(){
        return new CurpProperties();
    }
}
