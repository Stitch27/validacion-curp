package com.fincomun.validadorcurp.bean;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
public class HttpClientsBeans {


    private final Integer REINTENTOS = 3;

    public HttpClientsBeans() {
        
    }

    @Bean("simpleHttpClient")
    public OkHttpClient simpleHttpClient(){

        Interceptor interceptor = chain -> {
            Request request = chain.request();

            // try the request
            Response response = chain.proceed(request);

            int tryCount = 0;
            while (!response.isSuccessful() && tryCount < REINTENTOS) {

                log.error("intercept", "Request is not successful - " + tryCount);

                tryCount++;

                // retry the request
                response = chain.proceed(request);
            }

            // otherwise just pass the original response on
            return response;
        };

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(10000, TimeUnit.MILLISECONDS)
                .connectTimeout(10000, TimeUnit.MILLISECONDS)
                .writeTimeout(10000, TimeUnit.MILLISECONDS)
                .addInterceptor(interceptor)
                .build();


        return okHttpClient;
    }

}
