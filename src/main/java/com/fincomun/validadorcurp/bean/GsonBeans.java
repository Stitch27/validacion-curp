package com.fincomun.validadorcurp.bean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class GsonBeans {

    public GsonBeans() {
        
    }

    @Bean("simpleGson")
    public Gson gson (){
        return new GsonBuilder().serializeNulls().create();
    }

}
