package com.fincomun.procesaarchivo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DatosPortal {

    @JsonProperty("nombre")
    private final String nombre = "Masivos";
    @JsonProperty("identificador")
    private final String identificador = "106";
}
