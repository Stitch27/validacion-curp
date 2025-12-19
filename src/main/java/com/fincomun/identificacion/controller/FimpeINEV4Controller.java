package com.fincomun.identificacion.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.fincomun.identificacion.service.FimpeINEService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.fincomun.identificacion.model.RequestIdentificacionModel;

@Slf4j
@RestController
@RequestMapping(value = "/middleware")
public class FimpeINEV4Controller {

    @Autowired
    private FimpeINEService servicio;

    @PostMapping("/validar/identificacion")
    public ResponseEntity<Object> controlador(@RequestBody(required = false) RequestIdentificacionModel peticion) {

        log.info("");
        log.info("");
        log.info("---------- SERVICIO IDENTIFICACION (INE) 4.0 ----------");
        log.info("");
        log.info("");

        return servicio.servicio(peticion);

    }

}
