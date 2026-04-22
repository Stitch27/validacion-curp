package com.fincomun.validadorcurp.model;

import lombok.Data;
import com.fincomun.identificacion.model.PortalModel;

@Data
public class ClaveModel {

    private String proceso;

    private PortalModel datos_portal;

    private ClienteClaveModel datos_cliente;

}
