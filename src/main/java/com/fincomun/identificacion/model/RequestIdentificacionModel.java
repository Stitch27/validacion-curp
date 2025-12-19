package com.fincomun.identificacion.model;

import lombok.Data;

@Data
public class RequestIdentificacionModel {

    private PortalModel datos_portal;

    private DatosClienteModel datos_cliente;

}
