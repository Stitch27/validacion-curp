package com.fincomun.validadorcurp.bean;

import com.fincomun.validadorcurp.dto.properties.CurpProperties;
import com.twocaptcha.TwoCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwoCaptchaConfig {


    private final CurpProperties curpProperties;

    @Autowired
    public TwoCaptchaConfig(CurpProperties curpProperties) {
        this.curpProperties = curpProperties;
    }

    @Bean("twoCaptchaFirstKey")
    public TwoCaptcha singleKey(){
        TwoCaptcha twoCaptcha = new TwoCaptcha(curpProperties.twoCaptchaKey);
        twoCaptcha.setDefaultTimeout(120);
        twoCaptcha.setRecaptchaTimeout(120);
        twoCaptcha.setPollingInterval(5);
        return twoCaptcha;
    }
}
