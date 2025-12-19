package com.fincomun.validadorcurp.service;

import java.util.HashMap;
import java.util.Objects;
import org.json.JSONObject;
import java.util.LinkedHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.fincomun.validadorcurp.component.ConsultasClaveComponent;

@Slf4j
@Service
public class ActualizarPeticionesService {

    @Autowired
    private ConsultasClaveComponent componente;

    public ResponseEntity<Object> actualizar(String solicitud) {

        log.info("=============== ActualizarPeticionesService ===============");
        log.info("");

        HashMap<String, String> resultado = new LinkedHashMap<>();
        HashMap<String, Object> respuesta = new LinkedHashMap<>();

        if (Objects.isNull(solicitud)) {

            resultado.put("codigo", "101");
            resultado.put("descripcion", "Solicitud vacía.");
            respuesta.put("resultado", resultado);

            log.info(new JSONObject(respuesta) + "");
            log.info("");

            return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);

        }

        if (validar(solicitud) == 1) {

            resultado.put("codigo", "102");
            resultado.put("descripcion", "Solicitud incorrecta.");
            respuesta.put("resultado", resultado);

            log.info(new JSONObject(respuesta) + "");
            log.info("");

            return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);

        }

        JSONObject peticion = new JSONObject(solicitud);

        if (!peticion.has("tipo") || (peticion.get("tipo") + "").trim().isEmpty()) {

            resultado.put("codigo", "103");
            resultado.put("descripcion", "Ingresar el campo tipo.");
            respuesta.put("resultado", resultado);

            log.info(new JSONObject(respuesta) + "");
            log.info("");

            return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);

        }

        if (!StringUtils.isNumeric((peticion.get("tipo") + "").trim())) {

            resultado.put("codigo", "104");
            resultado.put("descripcion", "El campo tipo debe ser numérico.");
            respuesta.put("resultado", resultado);

            log.info(new JSONObject(respuesta) + "");
            log.info("");

            return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);

        }

        if (!peticion.has("identificador") || (peticion.get("identificador") + "").trim().isEmpty()) {

            resultado.put("codigo", "105");
            resultado.put("descripcion", "Ingresar el campo identificador.");
            respuesta.put("resultado", resultado);

            log.info(new JSONObject(respuesta) + "");
            log.info("");

            return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);

        }

        if (!StringUtils.isNumeric((peticion.get("identificador") + "").trim())) {

            resultado.put("codigo", "106");
            resultado.put("descripcion", "El campo identificador debe ser numérico.");
            respuesta.put("resultado", resultado);

            log.info(new JSONObject(respuesta) + "");
            log.info("");

            return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);

        }

        Integer actualizar = componente.actualizar_peticiones(Integer.parseInt((peticion.get("tipo") + "").trim() + ""), Integer.parseInt((peticion.get("identificador") + "").trim() + ""));

        log.info("Actualizar: " + actualizar);

        if (actualizar == 0) {

            resultado.put("codigo", "100");
            resultado.put("descripcion", "Petición procesada con éxito.");

            respuesta.put("resultado", resultado);
            return new ResponseEntity(respuesta, HttpStatus.OK);

        } else {

            resultado.put("codigo", "107");
            resultado.put("descripcion", "No fue posible actualizar el número de peticiones.");

            respuesta.put("resultado", resultado);
            return new ResponseEntity(respuesta, HttpStatus.CONFLICT);

        }

    }

    private int validar(String solicitud) {

        ObjectMapper mapeador = new ObjectMapper();

        try {

            mapeador.readTree(solicitud);

        } catch (Exception e) {

            return 1;

        }

        return 0;

    }

}
