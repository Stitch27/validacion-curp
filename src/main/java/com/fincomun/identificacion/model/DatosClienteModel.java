package com.fincomun.identificacion.model;

import lombok.Data;

@Data
public class DatosClienteModel {

    private String nombre;

    private String apellido_paterno;

    private String apellido_materno;

    private String reconocimiento_optico;

    private String a_registro;

    private String a_emision;

    private String clave_unica;

    private String n_emision;

    private String clave_elector;

    private String codigo_identificacion;

    private String rostro1;

}
