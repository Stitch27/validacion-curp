package com.fincomun.procesaarchivo.model;

import lombok.Data;

@Data
public class ResultCURPS {

    private String CURP;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombres;
    private String sexo;
    private String fechNac;
    private String nacionalidad;
    private String docProbatorio;
    private String anioReg;
    private String foja;
    private String tomo;
    private String libro;
    private String numActa;
    private String CRIP;
    private String numEntidadReg;
    private String cveMunicipioReg;
    private String NumRegExtranjeros;
    private String FolioCarta;
    private String FolioCertificado;
    private String cveEntidadNac;
    private String cveEntidadEmisora;
    private String statusCurp;
}
