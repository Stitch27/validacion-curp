package com.fincomun.validadorcurp.dto.network.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fincomun.validadorcurp.dto.common.SimpleResponse;
import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.ToString;

import java.lang.reflect.InvocationTargetException;

@Data
@ToString(callSuper = true)
public class BasicNetworkResponse<T> extends SimpleResponse {

    public BasicNetworkResponse() {
    }

    public BasicNetworkResponse(Class<T> responseClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        response = responseClass.getConstructor().newInstance();
    }

    private T response;

    @Expose(serialize = false, deserialize = false)
    @JsonIgnore
    protected Integer httpCode = 500;

    @Expose(serialize = false, deserialize = false)
    @JsonIgnore
    protected String httpMessage = "La petición no se ejecuto";

    @Expose(serialize = false, deserialize = false)
    @JsonIgnore
    private Boolean exitosa = false;
}
