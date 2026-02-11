package com.fincomun.procesaarchivo.dto;

import lombok.Data;

@Data
public class RegistraArchivoDto {

    private String solClienteCurp;
    private String serCodigo;
    private String serDescripcion;
    private String serRespuesta;
    private String bitSolicitud;
    private String bitRespuesta;
    private Integer estado;
    
}
