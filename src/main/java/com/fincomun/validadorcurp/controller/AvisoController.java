package com.fincomun.validadorcurp.controller;

import java.util.HashMap;
import org.json.JSONObject;
import java.util.LinkedHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.converter.HttpMessageNotReadableException;

@Slf4j
@RestControllerAdvice
public class AvisoController {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> aviso() {

        log.info("");
        log.info("=============== AvisoController ===============");
        log.info("");

        HashMap<String, String> resultado = new LinkedHashMap<>();
        HashMap<String, Object> respuesta = new LinkedHashMap<>();

        resultado.put("codigo", "-50");
        resultado.put("descripcion", "Solicitud incorrecta.");
        respuesta.put("resultado", resultado);

        log.info(new JSONObject(respuesta) + "");
        log.info("");

        return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);

    }

}
