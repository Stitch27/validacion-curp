package com.fincomun.procesaarchivo.controller;

import com.fincomun.procesaarchivo.service.ProcesaArchivoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LeerArchivoRespuestaController {

    private final ProcesaArchivoService procesaArchivoService;

    @RequestMapping(method = RequestMethod.POST, path = "/middleware/procesa_archivo", produces = "application/json")
    public ResponseEntity procesaArchivo(@RequestParam String nombreArchivo) {
        log.info("");
        log.info("=============== LeerArchivoRespuestaController, Metodo: procesaArchivo ===============");
        log.info("");
        if (nombreArchivo == null || nombreArchivo.isEmpty()) {
            log.info("parametro nombreArchivo requerido");
        }
        procesaArchivoService.procesaContenidoArchivo(nombreArchivo);
        log.info("");
        log.info("=============== finaliza: procesaArchivo ===============");
        return new ResponseEntity<>("Procesando archivo", HttpStatus.OK);
    }
}
