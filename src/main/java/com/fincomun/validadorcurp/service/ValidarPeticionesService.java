package com.fincomun.validadorcurp.service;

import java.util.List;
import java.util.HashMap;
import java.util.Objects;
import java.util.LinkedHashMap;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import com.fincomun.validadorcurp.model.ValidarPeticionesModel;
import com.fincomun.validadorcurp.component.ConsultasClaveComponent;
import com.fincomun.validadorcurp.utilities.ValidarPeticionesUtilities;
import com.fincomun.validadorcurp.utilities.ConstantesValidarPeticionesUtilities;

@Service
public class ValidarPeticionesService {

    @Autowired
    private ValidarPeticionesUtilities utilidades;

    @Autowired
    private ConsultasClaveComponent componente;

    private static final Logger LOG = Logger.getLogger(ValidarPeticionesService.class);

    public ResponseEntity<Object> validador(ValidarPeticionesModel solicitud) {

        HashMap<String, String> resultado = new LinkedHashMap<>();
        HashMap<String, Object> respuesta = new LinkedHashMap<>();

        if (Objects.isNull(solicitud)) {

            resultado.put("codigo", ConstantesValidarPeticionesUtilities.CODIGO_SOLICITUD_VACIA);
            resultado.put("descripcion", ConstantesValidarPeticionesUtilities.DESCRIPCION_SOLICITUD_VACIA);

            respuesta.put("resultado", resultado);
            return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);
        }

        Integer resultado_tipo = utilidades.tipo(solicitud);

        if (resultado_tipo != 0) {

            switch (resultado_tipo) {

                case 1:

                    resultado.put("codigo", ConstantesValidarPeticionesUtilities.CODIGO_CAMPO_TIPO);
                    resultado.put("descripcion", ConstantesValidarPeticionesUtilities.DESCRIPCION_CAMPO_TIPO);

                    respuesta.put("resultado", resultado);
                    break;

                case 2:

                    resultado.put("codigo", ConstantesValidarPeticionesUtilities.CODIGO_INGRESAR_TIPO);
                    resultado.put("descripcion", ConstantesValidarPeticionesUtilities.DESCRIPCION_INGRESAR_TIPO);

                    respuesta.put("resultado", resultado);
                    break;

                case 3:

                    resultado.put("codigo", ConstantesValidarPeticionesUtilities.CODIGO_TIPO_NUMERICO);
                    resultado.put("descripcion", ConstantesValidarPeticionesUtilities.DESCRIPCION_TIPO_NUMERICO);

                    respuesta.put("resultado", resultado);
                    break;

            }

            return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);

        }

        Integer resultado_peticiones = utilidades.peticiones(solicitud);

        if (resultado_peticiones != 0) {

            switch (resultado_peticiones) {

                case 1:

                    resultado.put("codigo", ConstantesValidarPeticionesUtilities.CODIGO_CAMPO_PETICIONES);
                    resultado.put("descripcion", ConstantesValidarPeticionesUtilities.DESCRIPCION_CAMPO_PETICIONES);

                    respuesta.put("resultado", resultado);
                    break;

                case 2:

                    resultado.put("codigo", ConstantesValidarPeticionesUtilities.CODIGO_NUMERO_PETICIONES);
                    resultado.put("descripcion", ConstantesValidarPeticionesUtilities.DESCRIPCION_NUMERO_PETICIONES);

                    respuesta.put("resultado", resultado);
                    break;

                case 3:

                    resultado.put("codigo", ConstantesValidarPeticionesUtilities.CODIGO_PETICIONES_NUMERICO);
                    resultado.put("descripcion", ConstantesValidarPeticionesUtilities.DESCRIPCION_PETICIONES_NUMERICO);

                    respuesta.put("resultado", resultado);
                    break;

            }

            return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);

        }

        Integer resultado_configuracion = utilidades.configuracion(solicitud);

        if (resultado_configuracion != 0) {

            switch (resultado_configuracion) {

                case 1:

                    resultado.put("codigo", ConstantesValidarPeticionesUtilities.CODIGO_INFORMACION_CONFIGURACION);
                    resultado.put("descripcion", ConstantesValidarPeticionesUtilities.DESCRIPCION_INFORMACION_CONFIGURACION);

                    respuesta.put("resultado", resultado);
                    break;

                case 2:

                    resultado.put("codigo", ConstantesValidarPeticionesUtilities.CODIGO_CAMPO_INSTITUCION);
                    resultado.put("descripcion", ConstantesValidarPeticionesUtilities.DESCRIPCION_CAMPO_INSTITUCION);

                    respuesta.put("resultado", resultado);
                    break;

                case 3:

                    resultado.put("codigo", ConstantesValidarPeticionesUtilities.CODIGO_INSTITUCION);
                    resultado.put("descripcion", ConstantesValidarPeticionesUtilities.DESCRIPCION_INSTITUCION);

                    respuesta.put("resultado", resultado);
                    break;

                case 4:

                    resultado.put("codigo", ConstantesValidarPeticionesUtilities.CODIGO_CAMPO_IDENTIFICADOR);
                    resultado.put("descripcion", ConstantesValidarPeticionesUtilities.DESCRIPCION_CAMPO_IDENTIFICADOR);

                    respuesta.put("resultado", resultado);
                    break;

                case 5:

                    resultado.put("codigo", ConstantesValidarPeticionesUtilities.CODIGO_IDENTIFICADOR);
                    resultado.put("descripcion", ConstantesValidarPeticionesUtilities.DESCRIPCION_IDENTIFICADOR);

                    respuesta.put("resultado", resultado);
                    break;

                case 6:

                    resultado.put("codigo", ConstantesValidarPeticionesUtilities.CODIGO_IDENTIFICADOR_NUMERICO);
                    resultado.put("descripcion", ConstantesValidarPeticionesUtilities.DESCRIPCION_IDENTIFICADOR_NUMERICO);

                    respuesta.put("resultado", resultado);
                    break;

            }

            return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);

        }

        Integer resultado_proceso = utilidades.proceso(solicitud);

        if (resultado_proceso != 100 && resultado_proceso != 200) {

            switch (resultado_proceso) {

                case 1:

                    resultado.put("codigo", ConstantesValidarPeticionesUtilities.CODIGO_PROCESO_INCORRECTO);
                    resultado.put("descripcion", ConstantesValidarPeticionesUtilities.DESCRIPCION_PROCESO_INCORRECTO);

                    respuesta.put("resultado", resultado);
                    break;

                case 2:

                    resultado.put("codigo", ConstantesValidarPeticionesUtilities.CODIGO_PETICIONES_INVALIDAS);
                    resultado.put("descripcion", ConstantesValidarPeticionesUtilities.DESCRIPCION_PETICIONES_INVALIDAS);

                    respuesta.put("resultado", resultado);
                    break;

            }

            return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);

        }

        List<Integer> fila = componente.obtener_configuracion(Integer.parseInt(solicitud.getConfiguracion().getIdentificador()), solicitud.getConfiguracion().getInstitucion(), 1);

        if (fila.get(0) != 0) {

            resultado.put("codigo", ConstantesValidarPeticionesUtilities.CODIGO_CONFIGUCACION);
            resultado.put("descripcion", ConstantesValidarPeticionesUtilities.DESCRIPCION_CONFIGURACION);

            respuesta.put("resultado", resultado);
            return new ResponseEntity(respuesta, HttpStatus.CONFLICT);

        }

        Integer peticiones_permitidas = Integer.parseInt(fila.get(1) + "");
        Integer peticiones_realizadas = Integer.parseInt(fila.get(2) + "");

        if (resultado_proceso == 100) {

            return utilidades.individual(peticiones_permitidas, peticiones_realizadas);

        } else {

            return utilidades.masivo(peticiones_permitidas, peticiones_realizadas, Integer.parseInt(solicitud.getNumero_peticiones()));

        }

    }

}
