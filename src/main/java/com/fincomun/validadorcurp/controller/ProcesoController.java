package com.fincomun.validadorcurp.controller;

import com.fincomun.validadorcurp.service.ProcesoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProcesoController {

    @Autowired
    private ProcesoService servicio;

    @GetMapping("/middleware/proceso")
    public String proceso(@RequestParam(name = "tipo") Integer tipo) {

        servicio.secuencia(tipo);

        return ".......... Inicio Proceso ..........";

    }

}
