package com.fincomun.identificacion.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class StartupLoggerComponent {

    private static final Logger log_clave = LoggerFactory.getLogger("com.fincomun.validadorcurp");
    private static final Logger log_identificacion = LoggerFactory.getLogger("com.fincomun.identificacion");

    @PostConstruct
    public void init() {

        log_clave.info("---------- APLICACION INICIADA ----------");
        log_clave.info("");
        log_clave.info("");

        log_identificacion.info("---------- APLICACION INICIADA ----------");
        log_identificacion.info("");
        log_identificacion.info("");

    }

}
