package com.fincomun.validadorcurp.bean;

import com.fincomun.validadorcurp.dto.properties.CurpProperties;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.validation.constraints.NotNull;

@Slf4j
@Validated
@Configuration
public class RetrofitBeans {

    private final CurpProperties curpProperties;

    @Autowired
    public RetrofitBeans(@NotNull CurpProperties curpProperties) {
        this.curpProperties = curpProperties;
    }


    @Autowired
    @Bean("antiCaptchaRetrofit")
    public Retrofit antiCaptchaRetrofit(@Qualifier("simpleHttpClient") @NotNull OkHttpClient okHttpClient){
        return  new Retrofit
                .Builder()
                .baseUrl(curpProperties.urlApiAntiCaptcha)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    @Autowired
    @Bean("renapoRetrofit")
    public Retrofit renapoRetrofit(@Qualifier("simpleHttpClient") @NotNull OkHttpClient okHttpClient){
        return  new Retrofit
                .Builder()
                .baseUrl(curpProperties.urlRenapo)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
