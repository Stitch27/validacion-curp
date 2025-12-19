package com.fincomun.validadorcurp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.fincomun.validadorcurp.model.ValidarPeticionesModel;
import com.fincomun.validadorcurp.service.ValidarPeticionesService;

@RestController
public class ValidarPeticionesController {

    @Autowired
    private ValidarPeticionesService servicio;

    @PostMapping("middleware/validador-peticiones")
    public ResponseEntity<Object> validador(@RequestBody(required = false) ValidarPeticionesModel solicitud) {

        return servicio.validador(solicitud);

    }

}
