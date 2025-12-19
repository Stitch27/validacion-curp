package com.fincomun.validadorcurp.model;

import com.fincomun.identificacion.model.PortalModel;
import lombok.Data;

@Data
public class ClaveModel {

    private String proceso;

    private PortalModel datos_portal;

    private ClienteClaveModel datos_cliente;

}
