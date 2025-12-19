
package com.fincomun.validadorcurp.dto.network.response.renapo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Registro {

    @JsonIgnore
    @SerializedName("parametro")
    @Expose
    private String parametro;

    @SerializedName("fechaNacimiento")
    @Expose
    private String fechaNacimiento;

    @SerializedName("docProbatorio")
    @Expose
    private Integer docProbatorio;

    @SerializedName("segundoApellido")
    @Expose
    private String segundoApellido;

    @SerializedName("curp")
    @Expose
    private String curp;

    @SerializedName("nombres")
    @Expose
    private String nombres;

    @SerializedName("primerApellido")
    @Expose
    private String primerApellido;

    @SerializedName("sexo")
    @Expose
    private String sexo;

    @SerializedName("claveEntidad")
    @Expose
    private String claveEntidad;

    @SerializedName("statusCurp")
    @Expose
    private String statusCurp;

    @SerializedName("nacionalidad")
    @Expose
    private String nacionalidad;

    @SerializedName("entidad")
    @Expose
    private String entidad;

    @SerializedName("datosDocProbatorio")
    @Expose
    private DatosDocProbatorio datosDocProbatorio = new DatosDocProbatorio();

}
