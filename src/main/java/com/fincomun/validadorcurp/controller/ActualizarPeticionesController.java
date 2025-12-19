package com.fincomun.validadorcurp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.fincomun.validadorcurp.service.ActualizarPeticionesService;

@Slf4j
@RestController
public class ActualizarPeticionesController {

    @Autowired
    private ActualizarPeticionesService servicio;

    @PostMapping("middleware/actualizar-peticiones")
    public ResponseEntity<Object> actualizar(@RequestBody(required = false) String solicitud) {

        log.info("");
        log.info("=============== ActualizarPeticionesController ===============");
        log.info("");

        log.info(solicitud);
        log.info("");

        return servicio.actualizar(solicitud);

    }

}
