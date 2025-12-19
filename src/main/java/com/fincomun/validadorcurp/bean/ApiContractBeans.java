package com.fincomun.validadorcurp.bean;

import com.fincomun.validadorcurp.network.apicontract.AntiCaptchaApiContract;
import com.fincomun.validadorcurp.network.apicontract.RenapoApiContract;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import retrofit2.Retrofit;

@Slf4j
@Validated
@Configuration
public class ApiContractBeans {

    public ApiContractBeans() {
        
    }

    @Bean
    @Autowired
    public AntiCaptchaApiContract antiCaptchaApiContract (@Qualifier("antiCaptchaRetrofit") Retrofit retrofit){
        return retrofit.create(AntiCaptchaApiContract.class);
    }

    @Bean
    @Autowired
    public RenapoApiContract renapoApiContract(@Qualifier("renapoRetrofit") Retrofit retrofit){
        return retrofit.create(RenapoApiContract.class);
    }

}
