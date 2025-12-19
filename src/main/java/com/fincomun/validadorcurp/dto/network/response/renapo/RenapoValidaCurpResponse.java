
package com.fincomun.validadorcurp.dto.network.response.renapo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fincomun.validadorcurp.dto.common.SimpleResponse;
import com.fincomun.validadorcurp.dto.controller.response.ConsultaCompletaCurpResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class RenapoValidaCurpResponse {

    @SerializedName("mensaje")
    @Expose
    private String mensaje = "No se pudo conectar con RENAPO";
    @SerializedName("codigo")
    @Expose
    private Integer codigo = -99;
    @SerializedName("registros")
    @Expose
    private List<Registro> registros = new ArrayList<>();

    @JsonIgnore
    public ConsultaCompletaCurpResponse getConsultaCompletaCurp (){
        ConsultaCompletaCurpResponse response = new ConsultaCompletaCurpResponse();

        response.setMensaje(this.mensaje);
        response.setCodigo(this.codigo);

        if(registros != null && !registros.isEmpty())
            response.setExpediente(registros.get(0));

        return response;
    }

    @JsonIgnore
    public SimpleResponse getSimpleResponse(){
        return new SimpleResponse(this.codigo, this.mensaje);
    }

}
