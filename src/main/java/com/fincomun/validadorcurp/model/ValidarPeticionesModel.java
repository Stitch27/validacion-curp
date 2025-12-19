package com.fincomun.validadorcurp.model;

import lombok.Data;

@Data
public class ValidarPeticionesModel {

    private String tipo;

    private String numero_peticiones;

    private ConfiguracionModel configuracion;

}
