package com.fincomun.validadorcurp.dto.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ToString
public class SimpleResponse {

    public SimpleResponse() {
    }

    public SimpleResponse(Integer codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    @NotNull
    protected Integer codigo = -1;

    @NotEmpty
    protected String mensaje = "La petición no se pudo procesar";

    @JsonIgnore
    public SimpleResponse getSimpleResponse(){
        return this;
    }

}
