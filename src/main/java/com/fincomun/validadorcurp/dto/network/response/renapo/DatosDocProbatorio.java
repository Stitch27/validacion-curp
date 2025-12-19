
package com.fincomun.validadorcurp.dto.network.response.renapo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DatosDocProbatorio {

    @SerializedName("entidadRegistro")
    @Expose
    private String entidadRegistro;
    @SerializedName("tomo")
    @Expose
    private String tomo;
    @SerializedName("claveMunicipioRegistro")
    @Expose
    private String claveMunicipioRegistro;
    @SerializedName("anioReg")
    @Expose
    private String anioReg;
    @SerializedName("claveEntidadRegistro")
    @Expose
    private String claveEntidadRegistro;
    @SerializedName("foja")
    @Expose
    private String foja;
    @SerializedName("numActa")
    @Expose
    private String numActa;
    @SerializedName("libro")
    @Expose
    private String libro;
    @SerializedName("municipioRegistro")
    @Expose
    private String municipioRegistro;
}
