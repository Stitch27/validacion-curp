package com.fincomun.validadorcurp.bean;

import com.fincomun.validadorcurp.dto.network.request.anticaptcha.CreateTaskRequest;
import com.fincomun.validadorcurp.dto.properties.CurpProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiCredentialBeans {

    private final CurpProperties curpProperties;

    @Autowired
    public ApiCredentialBeans(CurpProperties curpProperties) {
        this.curpProperties = curpProperties;
    }

    @Bean
    public CreateTaskRequest taskRequestBean (){
        CreateTaskRequest request = new CreateTaskRequest();
        request.setClientKey(curpProperties.tokenApiAntiCaptcha);
        request.getTask().setType("RecaptchaV2TaskProxyless");
        request.getTask().setWebsiteURL(curpProperties.urlSiteApiAntiCaptcha);
        request.getTask().setWebsiteKey(curpProperties.gTokenRenapo);

        return request;
    }

}
