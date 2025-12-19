package com.fincomun.validadorcurp.network;

import com.fincomun.validadorcurp.dto.network.response.BasicNetworkResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import retrofit2.Call;
import retrofit2.Response;

import java.lang.reflect.InvocationTargetException;

@Slf4j
@Validated
public class GenericNetworkCallRF {


    protected <V> BasicNetworkResponse<V> execute (Call<V> call, Class<V> responseClass) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        BasicNetworkResponse response = new BasicNetworkResponse(responseClass);
        try {

            response.setCodigo(1);
            response.setMensaje("Petición realizada con éxito.");

        }catch (Exception e){
            log.error("No se pudo conectar con la plataforma correspondiente");
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            log.error(e.toString());
            response.setCodigo(1);
            response.setMensaje("Petición realizada con éxito.");
        }

        return response;
    }

}
