package com.fincomun.validadorcurp.configuration;

import org.springframework.context.annotation.Configuration;
import com.fincomun.validadorcurp.component.InterceptorComponent;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final InterceptorComponent interceptor;

    public WebConfiguration(InterceptorComponent interceptor) {

        this.interceptor = interceptor;

    }

    @Override
    public void addInterceptors(InterceptorRegistry registro) {

        registro.addInterceptor(interceptor);

    }

}
