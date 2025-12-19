package com.fincomun.procesaarchivo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Data;

@Data
public class PeticionRenapoJson {

    @JsonProperty("datos_cliente")
    private DatosClienteRenapo datosCliente;

    @JsonProperty("proceso")
    private String proceso = "10";

    @JsonProperty("datos_portal")
    private DatosPortal datosPortal;

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
