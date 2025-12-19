package com.fincomun.validadorcurp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.fincomun.validadorcurp.service.ValidarClaveService;

@Slf4j
@RestController
public class ValidarClaveController {

    @Autowired
    private ValidarClaveService servicio;

    @PostMapping("middleware/validar/clave-unica")
    public ResponseEntity<Object> controlador(@RequestBody(required = false) String solicitud) {

        log.info("");
        log.info("");
        log.info("=============== ValidarClaveController ===============");
        log.info("");
        log.info("");

        log.info("Peticion: ");
        log.info(solicitud);
        log.info("");
        log.info("");

        return servicio.servicio(solicitud);

    }

}
