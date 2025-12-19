package com.fincomun.validadorcurp.network;

import com.fincomun.validadorcurp.dto.network.response.BasicNetworkResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.hibernate.validator.constraints.URL;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

@Slf4j
@Validated
public class GenericNetworkCallRT {

    private final HttpClient httpClient;


    public GenericNetworkCallRT(){
        httpClient = HttpClients.custom()
                .setRetryHandler((e, i, httpContext) -> {
                    log.info("Ocurrio un error al lanzar la petición");
                    log.info(e.getMessage());
                    log.info(e.getCause().getStackTrace().toString());
                    log.info("Reintentantdo ...");
                    return i > 3;
                })
                .build();
    }


    protected <Req, Res>BasicNetworkResponse<Res> execute(@NotNull HttpMethod httpMethod, @URL String url, HashMap<String,String> urlParams,Req request, @NotNull Class<Res> responseClass, @NotEmpty HashMap<String, String> headers) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException{
        BasicNetworkResponse response = new BasicNetworkResponse(responseClass);
        try{
            RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));
            HttpHeaders httpHeaders = new HttpHeaders();
            if(headers != null)
                headers.forEach( (k,v) ->  httpHeaders.add(k, v) );

            if (urlParams != null){
                log.info("Petición con parámetros en la URL...");
                MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
                urlParams.forEach((k,v) -> multiValueMap.add(k,String.valueOf(v)));
                UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(url).queryParams(multiValueMap);
                url = uriComponentsBuilder.build().encode().toUriString();
            }

            log.info("Método a consumir : " + httpMethod.name());
            log.info("Url a consumir : " + url);
            log.info("Headers de entrada : " +  headers.toString());
            log.info("Datos de entrada : " + request != null ?request.toString(): "Sin request");

            HttpEntity<Req> httpEntity = new HttpEntity<>(request, httpHeaders);
            ResponseEntity<Res> responseEntity = restTemplate.exchange(url,httpMethod,httpEntity,responseClass);

            log.info("Código HTTP obtenido : " + responseEntity.getStatusCodeValue());
            log.info("Mensaje HTTP obtenido : " + responseEntity.getStatusCode().getReasonPhrase());
            log.info("Respuesta obtenida : " + responseEntity.getBody().toString());

            response.setHttpCode(responseEntity.getStatusCodeValue());
            response.setHttpMessage(responseEntity.getStatusCode().getReasonPhrase());

            if (responseEntity.getStatusCode().is2xxSuccessful() || responseEntity.getStatusCode().is4xxClientError()){
                response.setExitosa(true);
                response.setCodigo(0);
                response.setMensaje("Exito");
                response.setResponse(responseEntity.getBody());
            }
        }catch (Exception e){
            log.error("Ocurrio un error al tratar de consumir : " + url);
            log.error(e.getMessage());
            log.error(e.getCause().getMessage());
            log.error(e.toString());
        }
        return response;
    }

}
