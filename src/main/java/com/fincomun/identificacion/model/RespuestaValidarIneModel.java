package com.fincomun.identificacion.model;

import lombok.Data;

@Data
public class RespuestaValidarIneModel {

    private String estatus;

    private String mensaje;

    private String codigoValidacion;

    private String claveMensaje;

    private String claveElector;

    private String numeroEmision;

    private String anioRegistro;

    private String anioEmision;

    private String vigencia;

    private String ocr;

    private String cic;

    private String distritoFederal;

    private String informacionAdicional;

    private String referencia;

    private String fecha;

}
