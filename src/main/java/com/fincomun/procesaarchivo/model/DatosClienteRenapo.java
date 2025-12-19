package com.fincomun.procesaarchivo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DatosClienteRenapo {

    @JsonProperty("clave_unica")
    private String claveUnica;
}
