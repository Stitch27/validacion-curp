package com.fincomun.procesaarchivo.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Data;

@Data
public class JsonRespuesta {

    private String codigoRespuesta;
    private String descripcionRespuesta;
    private String referencia;
    private RespuestaRENAPO respuestaRENAPO;

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
