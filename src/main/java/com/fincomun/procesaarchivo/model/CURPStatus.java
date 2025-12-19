package com.fincomun.procesaarchivo.model;

import lombok.Data;

@Data
public class CURPStatus {

    private String statusOper;
    private String message;
    private String tipoError;
    private String codigoError;
    private String sessionID;
    private ResultCURPS resultCURPS;
}
