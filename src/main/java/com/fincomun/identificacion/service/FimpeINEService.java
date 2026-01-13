package com.fincomun.identificacion.service;

import java.util.*;
import org.json.XML;
import org.json.JSONObject;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.HttpClient;
import org.springframework.http.HttpStatus;
import org.apache.http.entity.StringEntity;
import org.springframework.stereotype.Service;
import org.apache.http.client.methods.HttpPost;
import org.springframework.http.ResponseEntity;
import org.apache.http.client.config.RequestConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import com.fincomun.identificacion.utilities.FimpeINEUtilities;
import com.fincomun.identificacion.component.ServiciosComponent;
import com.fincomun.identificacion.model.RespuestaValidarIneModel;
import com.fincomun.identificacion.model.RequestIdentificacionModel;
import com.fincomun.identificacion.component.ProcedFimpeINEComponent;
import com.fincomun.identificacion.utilities.ConstantesFimpeINEUtilities;
import com.fincomun.identificacion.utilities.PropiedadesFimpeINEUtilities;

@Slf4j
@Service
public class FimpeINEService {

    @Autowired
    private FimpeINEUtilities utilidades;

    @Autowired
    private ServiciosComponent servicios;

    @Autowired
    private ProcedFimpeINEComponent componente;

    public ResponseEntity<Object> servicio(RequestIdentificacionModel peticion, String transaccion) {

        HashMap<String, String> resultado = new LinkedHashMap<>();
        HashMap<String, Object> respuesta = new LinkedHashMap<>();

        List<Integer> lista = componente.obtener_configuracion(2, "FIMPE", 1);

        if (lista.get(0) != 0) {

            resultado.put("codigo", ConstantesFimpeINEUtilities.CODIGO_RESPUESTA_ERROR_CONFIGURACION);
            resultado.put("descripcion", ConstantesFimpeINEUtilities.DESCRIPCION_RESPUESTA_ERROR_CONFIGURACION);
            respuesta.put("resultado", resultado);

            componente.registrar_bitacora(" ", new JSONObject(respuesta).toString(), 100);

            log.info("SALIDA (JSON): ");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");

            return new ResponseEntity(respuesta, HttpStatus.CONFLICT);

        }

        Integer validacion = utilidades.validar_peticiones(lista.get(1), lista.get(2));

        if (validacion != 0) {

            resultado.put("codigo", ConstantesFimpeINEUtilities.CODIGO_RESPUESTA_SIN_PETICIONES);
            resultado.put("descripcion", ConstantesFimpeINEUtilities.DESCRIPCION_RESPUESTA_SIN_PETICIONES);
            respuesta.put("resultado", resultado);

            componente.registrar_bitacora(" ", new JSONObject(respuesta).toString(), 100);

            log.info("SALIDA (JSON): ");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");

            return new ResponseEntity(respuesta, HttpStatus.CONFLICT);

        }

        if (Objects.isNull(peticion)) {

            resultado.put("codigo", ConstantesFimpeINEUtilities.IDENTIFICACION_CODIGO_VACIO);
            resultado.put("descripcion", ConstantesFimpeINEUtilities.IDENTIFICACION_DESCRIPCION_VACIO);
            respuesta.put("resultado", resultado);

            componente.registrar_bitacora(" ", new JSONObject(respuesta).toString(), 100);

            log.info("SALIDA (JSON): ");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");

            return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);

        }

        log.info("PARAMETROS DE ENTRADA");
        log.info("");
        log.info("DATOS PORTAL");
        log.info("NOMBRE: " + peticion.getDatos_portal().getNombre().trim());
        log.info("IDENTIFICADOR: " + peticion.getDatos_portal().getIdentificador().trim());
        log.info("");
        log.info("");
        log.info("DATOS CLIENTE");
        log.info("NOMBRE: " + peticion.getDatos_cliente().getNombre().trim());
        log.info("APELLIDO PATERNO: " + peticion.getDatos_cliente().getApellido_paterno().trim());
        log.info("APELLIDO MATERNO: " + peticion.getDatos_cliente().getApellido_materno().trim());
        log.info("OCR: " + peticion.getDatos_cliente().getReconocimiento_optico().trim());
        log.info("ANIO REGISTRO: " + peticion.getDatos_cliente().getA_registro().trim());
        log.info("ANIO EMISION: " + peticion.getDatos_cliente().getA_emision().trim());
        log.info("CURP: " + peticion.getDatos_cliente().getClave_unica().trim());
        log.info("NUMERO EMISION: " + peticion.getDatos_cliente().getN_emision().trim());
        log.info("CLAVE ELECTOR: " + peticion.getDatos_cliente().getClave_elector().trim());
        log.info("CIC: " + peticion.getDatos_cliente().getCodigo_identificacion().trim());
        log.info("");
        log.info("");

        Integer resultado_portal = utilidades.validar_portal(peticion);

        if (resultado_portal != 0) {

            switch (resultado_portal) {

                case 1:
                    resultado.put("codigo", ConstantesFimpeINEUtilities.IDENTIFICACION_CODIGO_INFORMACION_PORTAL);
                    resultado.put("descripcion", ConstantesFimpeINEUtilities.IDENTIFICACION_DESCRIPCION_INFORMACION_PORTAL);
                    respuesta.put("resultado", resultado);

                    break;

                case 2:
                    resultado.put("codigo", ConstantesFimpeINEUtilities.IDENTIFICACION_CODIGO_CAMPO_IDENTIFICADOR_PORTAL);
                    resultado.put("descripcion", ConstantesFimpeINEUtilities.IDENTIFICACION_DESCRIPCION_CAMPO_IDENTIFICADOR_PORTAL);
                    respuesta.put("resultado", resultado);

                    break;

                case 3:
                    resultado.put("codigo", ConstantesFimpeINEUtilities.IDENTIFICACION_CODIGO_VALOR_IDENTIFICADOR_PORTAL);
                    resultado.put("descripcion", ConstantesFimpeINEUtilities.IDENTIFICACION_DESCRIPCION_VALOR_IDENTIFICADOR_PORTAL);
                    respuesta.put("resultado", resultado);

                    break;

                case 4:
                    resultado.put("codigo", ConstantesFimpeINEUtilities.IDENTIFICACION_CODIGO_VALOR_IDENTIFICADOR_PORTAL_NUMERO);
                    resultado.put("descripcion", ConstantesFimpeINEUtilities.IDENTIFICACION_DESCRIPCION_VALOR_IDENTIFICADOR_PORTAL_NUMERO);
                    respuesta.put("resultado", resultado);

                    break;

                case 5:
                    resultado.put("codigo", ConstantesFimpeINEUtilities.IDENTIFICACION_CODIGO_CAMPO_NOMBRE_PORTAL);
                    resultado.put("descripcion", ConstantesFimpeINEUtilities.IDENTIFICACION_DESCRIPCION_CAMPO_NOMBRE_PORTAL);
                    respuesta.put("resultado", resultado);

                    break;

                case 6:
                    resultado.put("codigo", ConstantesFimpeINEUtilities.IDENTIFICACION_CODIGO_VALOR_NOMBRE_PORTAL);
                    resultado.put("descripcion", ConstantesFimpeINEUtilities.IDENTIFICACION_DESCRIPCION_VALOR_NOMBRE_PORTAL);
                    respuesta.put("resultado", resultado);

                    break;

            }

            componente.registrar_bitacora(convertir_salida(peticion), new JSONObject(respuesta).toString(), 100);

            log.info("SALIDA (JSON): ");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");

            return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);

        }

        Integer codigo = componente.portal_valido(Integer.valueOf(peticion.getDatos_portal().getIdentificador().trim()),
                peticion.getDatos_portal().getNombre().trim());

        if (codigo != 0) {

            HttpStatus estatus = HttpStatus.BAD_REQUEST;

            switch (codigo) {

                case 1:
                    resultado.put("codigo", ConstantesFimpeINEUtilities.IDENTIFICACION_CODIGO_PORTAL_INACTIVO);
                    resultado.put("descripcion", ConstantesFimpeINEUtilities.IDENTIFICACION_DESCRIPCION_PORTAL_INACTIVO);
                    respuesta.put("resultado", resultado);
                    break;
                case 2:
                    resultado.put("codigo", ConstantesFimpeINEUtilities.IDENTIFICACION_CODIGO_PORTAL_INCORRECTO);
                    resultado.put("descripcion", ConstantesFimpeINEUtilities.IDENTIFICACION_DESCRIPCION_PORTAL_INCORRECTO);
                    respuesta.put("resultado", resultado);
                    break;
                case 3:
                case 4:
                case 5:
                    resultado.put("codigo", ConstantesFimpeINEUtilities.IDENTIFICACION_CODIGO_VALIDAR_PORTAL);
                    resultado.put("descripcion", ConstantesFimpeINEUtilities.IDENTIFICACION_DESCRIPCION_VALIDAR_PORTAL);
                    respuesta.put("resultado", resultado);

                    estatus = HttpStatus.SERVICE_UNAVAILABLE;
                    break;

            }

            componente.registrar_bitacora(convertir_salida(peticion), new JSONObject(respuesta).toString(), 100);

            log.info("SALIDA (JSON): ");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");

            return new ResponseEntity(respuesta, estatus);

        }

        Integer validar_usuario = utilidades.validar_cliente(peticion);

        Integer bitacora;

        if (validar_usuario != 0) {

            switch (validar_usuario) {
                case 1:
                    resultado.put("codigo", ConstantesFimpeINEUtilities.IDENTIFICACION_CODIGO_INFORMACION_CLIENTE);
                    resultado.put("descripcion", ConstantesFimpeINEUtilities.IDENTIFICACION_DESCRIPCION_INFORMACION_CLIENTE);
                    respuesta.put("resultado", resultado);

                    bitacora = componente.registrar_bitacora(convertir_salida(peticion), new JSONObject(respuesta).toString(), 100);

                    componente.registrar_solicitud(peticion.getDatos_portal().getNombre().trim(),
                            Integer.valueOf(peticion.getDatos_portal().getIdentificador().trim()),
                            " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", bitacora);

                    break;
                case 2:
                    resultado.put("codigo", ConstantesFimpeINEUtilities.IDENTIFICACION_CODIGO_NOMBRE_CLIENTE);
                    resultado.put("descripcion", ConstantesFimpeINEUtilities.IDENTIFICACION_DESCRIPCION_NOMBRE_CLIENTE);
                    respuesta.put("resultado", resultado);

                    bitacora = componente.registrar_bitacora(convertir_salida(peticion), new JSONObject(respuesta).toString(), 100);

                    componente.registrar_solicitud(peticion.getDatos_portal().getNombre().trim(),
                            Integer.valueOf(peticion.getDatos_portal().getIdentificador().trim()),
                            " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", bitacora);

                    break;
                case 3:
                    resultado.put("codigo", ConstantesFimpeINEUtilities.IDENTIFICACION_CODIGO_A_PATERNO_CLIENTE);
                    resultado.put("descripcion", ConstantesFimpeINEUtilities.IDENTIFICACION_DESCRIPCION_A_PATERNO_CLIENTE);
                    respuesta.put("resultado", resultado);

                    bitacora = componente.registrar_bitacora(convertir_salida(peticion), new JSONObject(respuesta).toString(), 100);

                    componente.registrar_solicitud(peticion.getDatos_portal().getNombre().trim(),
                            Integer.valueOf(peticion.getDatos_portal().getIdentificador().trim()),
                            peticion.getDatos_cliente().getNombre().trim(), " ", " ", " ", " ", " ", " ", " ", " ", " ", bitacora);

                    break;
                case 4:
                    resultado.put("codigo", ConstantesFimpeINEUtilities.IDENTIFICACION_CODIGO_A_MATERNO_CLIENTE);
                    resultado.put("descripcion", ConstantesFimpeINEUtilities.IDENTIFICACION_DESCRIPCION_A_MATERNO_CLIENTE);
                    respuesta.put("resultado", resultado);

                    bitacora = componente.registrar_bitacora(convertir_salida(peticion), new JSONObject(respuesta).toString(), 100);

                    componente.registrar_solicitud(peticion.getDatos_portal().getNombre().trim(),
                            Integer.valueOf(peticion.getDatos_portal().getIdentificador().trim()),
                            peticion.getDatos_cliente().getNombre().trim(), peticion.getDatos_cliente().getApellido_paterno().trim(), " ", " ", " ", " ",
                            " ", " ", " ", " ", bitacora);
                    break;
                case 5:
                    resultado.put("codigo", ConstantesFimpeINEUtilities.IDENTIFICACION_CODIGO_RECONOCIMIENTO_OPTICO);
                    resultado.put("descripcion", ConstantesFimpeINEUtilities.IDENTIFICACION_DESCRIPCION_RECONOCIMIENTO_OPTICO);
                    respuesta.put("resultado", resultado);

                    bitacora = componente.registrar_bitacora(convertir_salida(peticion), new JSONObject(respuesta).toString(), 100);

                    componente.registrar_solicitud(peticion.getDatos_portal().getNombre().trim(),
                            Integer.valueOf(peticion.getDatos_portal().getIdentificador().trim()),
                            peticion.getDatos_cliente().getNombre().trim(), peticion.getDatos_cliente().getApellido_paterno().trim(),
                            peticion.getDatos_cliente().getApellido_materno().trim(), " ", " ", " ", " ", " ", " ", " ", bitacora);
                    break;
                case 6:
                    resultado.put("codigo", ConstantesFimpeINEUtilities.IDENTIFICACION_CODIGO_A_REGISTRO);
                    resultado.put("descripcion", ConstantesFimpeINEUtilities.IDENTIFICACION_DESCRIPCION_A_REGISTRO);
                    respuesta.put("resultado", resultado);

                    bitacora = componente.registrar_bitacora(convertir_salida(peticion), new JSONObject(respuesta).toString(), 100);

                    componente.registrar_solicitud(peticion.getDatos_portal().getNombre().trim(),
                            Integer.valueOf(peticion.getDatos_portal().getIdentificador().trim()),
                            peticion.getDatos_cliente().getNombre().trim(), peticion.getDatos_cliente().getApellido_paterno().trim(),
                            peticion.getDatos_cliente().getApellido_materno().trim(), peticion.getDatos_cliente().getReconocimiento_optico().trim(),
                            " ", " ", " ", " ", " ", " ", bitacora);

                    break;
                case 7:
                    resultado.put("codigo", ConstantesFimpeINEUtilities.IDENTIFICACION_CODIGO_A_EMISION);
                    resultado.put("descripcion", ConstantesFimpeINEUtilities.IDENTIFICACION_DESCRIPCION_A_EMISION);
                    respuesta.put("resultado", resultado);

                    bitacora = componente.registrar_bitacora(convertir_salida(peticion), new JSONObject(respuesta).toString(), 100);

                    componente.registrar_solicitud(peticion.getDatos_portal().getNombre().trim(),
                            Integer.valueOf(peticion.getDatos_portal().getIdentificador().trim()),
                            peticion.getDatos_cliente().getNombre().trim(), peticion.getDatos_cliente().getApellido_paterno().trim(),
                            peticion.getDatos_cliente().getApellido_materno().trim(), peticion.getDatos_cliente().getReconocimiento_optico().trim(),
                            peticion.getDatos_cliente().getA_registro().trim(), " ", " ", " ", " ", " ", bitacora);
                    break;
                case 8:
                    resultado.put("codigo", ConstantesFimpeINEUtilities.IDENTIFICACION_CODIGO_CLAVE_UNICA);
                    resultado.put("descripcion", ConstantesFimpeINEUtilities.IDENTIFICACION_DESCRIPCION_CLAVE_UNICA);
                    respuesta.put("resultado", resultado);

                    bitacora = componente.registrar_bitacora(convertir_salida(peticion), new JSONObject(respuesta).toString(), 100);

                    componente.registrar_solicitud(peticion.getDatos_portal().getNombre().trim(),
                            Integer.valueOf(peticion.getDatos_portal().getIdentificador().trim()),
                            peticion.getDatos_cliente().getNombre().trim(), peticion.getDatos_cliente().getApellido_paterno().trim(),
                            peticion.getDatos_cliente().getApellido_materno().trim(), peticion.getDatos_cliente().getReconocimiento_optico().trim(),
                            peticion.getDatos_cliente().getA_registro().trim(), peticion.getDatos_cliente().getA_emision().trim(),
                            " ", " ", " ", " ", bitacora);

                    break;
                case 9:
                    resultado.put("codigo", ConstantesFimpeINEUtilities.IDENTIFICACION_CODIGO_NUMERO_EMISION);
                    resultado.put("descripcion", ConstantesFimpeINEUtilities.IDENTIFICACION_DESCRIPCION_NUMERO_EMISION);
                    respuesta.put("resultado", resultado);

                    bitacora = componente.registrar_bitacora(convertir_salida(peticion), new JSONObject(respuesta).toString(), 100);

                    componente.registrar_solicitud(peticion.getDatos_portal().getNombre().trim(),
                            Integer.valueOf(peticion.getDatos_portal().getIdentificador().trim()),
                            peticion.getDatos_cliente().getNombre().trim(), peticion.getDatos_cliente().getApellido_paterno().trim(),
                            peticion.getDatos_cliente().getApellido_materno().trim(), peticion.getDatos_cliente().getReconocimiento_optico().trim(),
                            peticion.getDatos_cliente().getA_registro().trim(), peticion.getDatos_cliente().getA_emision().trim(),
                            peticion.getDatos_cliente().getClave_unica().trim(), " ", " ", " ", bitacora);

                    break;
                case 10:
                    resultado.put("codigo", ConstantesFimpeINEUtilities.IDENTIFICACION_CODIGO_CLAVE_ELECTOR);
                    resultado.put("descripcion", ConstantesFimpeINEUtilities.IDENTIFICACION_DESCRIPCION_CLAVE_ELECTOR);
                    respuesta.put("resultado", resultado);

                    bitacora = componente.registrar_bitacora(convertir_salida(peticion), new JSONObject(respuesta).toString(), 100);

                    componente.registrar_solicitud(peticion.getDatos_portal().getNombre().trim(),
                            Integer.valueOf(peticion.getDatos_portal().getIdentificador().trim()),
                            peticion.getDatos_cliente().getNombre().trim(), peticion.getDatos_cliente().getApellido_paterno().trim(),
                            peticion.getDatos_cliente().getApellido_materno().trim(), peticion.getDatos_cliente().getReconocimiento_optico().trim(),
                            peticion.getDatos_cliente().getA_registro().trim(), peticion.getDatos_cliente().getA_emision().trim(),
                            peticion.getDatos_cliente().getClave_unica().trim(), peticion.getDatos_cliente().getN_emision().trim(),
                            " ", " ", bitacora);

                    break;
                case 11:
                    resultado.put("codigo", ConstantesFimpeINEUtilities.IDENTIFICACION_CODIGO_IDENTIFICACION);
                    resultado.put("descripcion", ConstantesFimpeINEUtilities.IDENTIFICACION_DESCRIPCION_CODIGO_IDENTIFICACION);
                    respuesta.put("resultado", resultado);

                    bitacora = componente.registrar_bitacora(convertir_salida(peticion), new JSONObject(respuesta).toString(), 100);

                    componente.registrar_solicitud(peticion.getDatos_portal().getNombre().trim(),
                            Integer.valueOf(peticion.getDatos_portal().getIdentificador().trim()),
                            peticion.getDatos_cliente().getNombre().trim(), peticion.getDatos_cliente().getApellido_paterno().trim(),
                            peticion.getDatos_cliente().getApellido_materno().trim(), peticion.getDatos_cliente().getReconocimiento_optico().trim(),
                            peticion.getDatos_cliente().getA_registro().trim(), peticion.getDatos_cliente().getA_emision().trim(),
                            peticion.getDatos_cliente().getClave_unica().trim(), peticion.getDatos_cliente().getN_emision().trim(),
                            peticion.getDatos_cliente().getClave_elector().trim(), " ", bitacora);

                    break;

            }

            log.info("SALIDA (JSON): ");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");

            return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);

        }

        String parametro = componente.obtener_parametro(PropiedadesFimpeINEUtilities.PARAMETRO_CONFIGURACION_SERVICIO_INE);

        log.info("PARAMETRO: ");
        log.info(parametro);
        log.info("");
        log.info("");

        if (parametro.equals("true")) {

            log.info("SE EJECUTA EL SERVICIO DE LISTA NOMINALES");
            log.info("");
            log.info("");

            String cic = peticion.getDatos_cliente().getCodigo_identificacion().trim();

            if (cic.length() > 9) {

                cic = cic.substring(0, 9);

            }

            String id_ciudadano = peticion.getDatos_cliente().getReconocimiento_optico().trim();

            if (id_ciudadano.length() > 9) {

                id_ciudadano = id_ciudadano.substring(id_ciudadano.length() - 9);

            }

            log.info("DATOS SERVICIO LISTA NOMINALES: ");
            log.info("");
            log.info("CIC: " + cic);
            log.info("ID CIUDADANO: " + id_ciudadano);
            log.info("");
            log.info("");

            RespuestaValidarIneModel respuesta_servicio = servicios.servicio_validar_ine(cic, id_ciudadano);

            if (Objects.isNull(respuesta_servicio)) {

                resultado.put("codigo", "130");
                resultado.put("descripcion", "No fue posible ejecutar de forma correcta el servicio de validación INE.");
                respuesta.put("resultado", resultado);

                log.info("SALIDA (JSON): ");
                log.info("");
                log.info(new JSONObject(respuesta).toString());
                log.info("");
                log.info("");

                return new ResponseEntity(respuesta, HttpStatus.CONFLICT);

            }

            Integer registrar = componente.registrar_datos_ine(cic, id_ciudadano, respuesta_servicio, transaccion);

            if (registrar != 0) {

                resultado.put("codigo", "131");
                resultado.put("descripcion", "No fue posible registrar la información en base de datos.");
                respuesta.put("resultado", resultado);

                log.info("SALIDA (JSON): ");
                log.info(new JSONObject(respuesta).toString());
                log.info("");
                log.info("");

                return new ResponseEntity(respuesta, HttpStatus.CONFLICT);

            }

            if (!respuesta_servicio.getClaveMensaje().equals("3")) {

                resultado.put("codigo", "132");
                resultado.put("descripcion", "Petición no válida.");
                respuesta.put("resultado", resultado);

            } else {

                resultado.put("codigo", "100");
                resultado.put("descripcion", "Petición realizada con éxito.");
                respuesta.put("resultado", resultado);

            }

            HashMap<String, Object> datos = new LinkedHashMap<>();
            datos.put("estatus", respuesta_servicio.getEstatus());
            datos.put("mensaje", respuesta_servicio.getMensaje());
            datos.put("codigoValidacion", respuesta_servicio.getCodigoValidacion());
            datos.put("claveMensaje", respuesta_servicio.getClaveMensaje());
            datos.put("claveElector", respuesta_servicio.getClaveElector());
            datos.put("numeroEmision", respuesta_servicio.getNumeroEmision());
            datos.put("anioRegistro", respuesta_servicio.getAnioRegistro());
            datos.put("anioEmision", respuesta_servicio.getAnioEmision());
            datos.put("vigencia", respuesta_servicio.getVigencia());
            datos.put("ocr", respuesta_servicio.getOcr());
            datos.put("cic", respuesta_servicio.getCic());
            datos.put("distritoFederal", respuesta_servicio.getDistritoFederal());
            datos.put("informacionAdicional", respuesta_servicio.getInformacionAdicional());

            respuesta.put("datos", datos);
            respuesta.put("transaccion", transaccion);

            log.info("SALIDA (JSON): ");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");

            log.info("REGISTROS EN BITACORA TRADICIONAL");
            log.info("");
            log.info("");

            bitacora = componente.registrar_bitacora(convertir_salida(peticion), new JSONObject(respuesta).toString(), 100);

            componente.registrar_solicitud(peticion.getDatos_portal().getNombre().trim(),
                    Integer.valueOf(peticion.getDatos_portal().getIdentificador().trim()),
                    peticion.getDatos_cliente().getNombre().trim(), peticion.getDatos_cliente().getApellido_paterno().trim(),
                    peticion.getDatos_cliente().getApellido_materno().trim(), peticion.getDatos_cliente().getReconocimiento_optico().trim(),
                    peticion.getDatos_cliente().getA_registro().trim(), peticion.getDatos_cliente().getA_emision().trim(),
                    peticion.getDatos_cliente().getClave_unica().trim(), peticion.getDatos_cliente().getN_emision().trim(),
                    peticion.getDatos_cliente().getClave_elector().trim(), peticion.getDatos_cliente().getCodigo_identificacion().trim(),
                    bitacora);

            return new ResponseEntity(respuesta, HttpStatus.OK);

        } else {

            log.info("SE EJECUTA EL SERVICIO DE FIMPE");
            log.info("");
            log.info("");

            return servicio_identificacion(peticion);

        }

    }

    private ResponseEntity<Object> servicio_identificacion(RequestIdentificacionModel peticion) {

        HashMap<String, String> resultado = new LinkedHashMap<>();
        HashMap<String, Object> respuesta = new LinkedHashMap<>();

        List<String> parametros = solicitud(peticion);

        RequestConfig configuracion = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(5000).setSocketTimeout(5000).build();
        HttpClient cliente = HttpClientBuilder.create().setDefaultRequestConfig(configuracion).build();

        HttpPost operacion = new HttpPost(PropiedadesFimpeINEUtilities.DIRECCION_IDENTIFICACION);
        operacion.addHeader("Content-Type", PropiedadesFimpeINEUtilities.CABECERA_IDENTIFICACION);

        String cuerpo = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"services.middleware.fimpe.org.mx\">\n"
                + "   <soapenv:Header/>\n"
                + "   <soapenv:Body>\n"
                + "      <ser:verificaDatos>\n"
                + "         <arg0>" + parametros.get(0) + "</arg0>\n"
                + "      </ser:verificaDatos>\n"
                + "   </soapenv:Body>\n"
                + "</soapenv:Envelope>";

        log.info("XML DE ENTRADA DEL SERVICIO: ");
        log.info(solicitud_sin_rostro(peticion));
        log.info("");
        log.info("");

        try {

            StringEntity entidad = new StringEntity(cuerpo);

            operacion.setEntity(entidad);

            HttpResponse servicio = cliente.execute(operacion);

            String salida = EntityUtils.toString(servicio.getEntity());

            if (servicio.getStatusLine().getStatusCode() == 200) {

                log.info("XML DE SALIDA DEL SERVICIO: ");
                log.info(salida);
                log.info("");
                log.info("");

                JSONObject object = XML.toJSONObject(salida);

                if (object.has("soap:Envelope")) {

                    JSONObject envelope = new JSONObject(object.get("soap:Envelope").toString());

                    if (envelope.has("soap:Body")) {

                        JSONObject body = new JSONObject(envelope.get("soap:Body").toString());

                        if (body.has("ns2:verificaDatosResponse")) {

                            JSONObject verifica = new JSONObject(body.get("ns2:verificaDatosResponse").toString());

                            if (verifica.has("return")) {

                                JSONObject json = new JSONObject(verifica.get("return").toString());

                                if (json.has("codigoRespuesta")) {

                                    if (!(json.get("codigoRespuesta") + "").trim().isEmpty()) {

                                        if ((json.get("codigoRespuesta") + "").trim().equals("00")) {

                                            resultado.put("codigo", ConstantesFimpeINEUtilities.IDENTIFICACION_CODIGO_EXITO);
                                            resultado.put("descripcion", ConstantesFimpeINEUtilities.IDENTIFICACION_DESCRIPCION_EXITO);
                                            respuesta.put("resultado", resultado);

                                            int bitacora_identificador = componente.registrar_bitacora(convertir_salida(peticion), new JSONObject(respuesta).toString(), 300);

                                            int solicitud_identificador = componente.registrar_solicitud(peticion.getDatos_portal().getNombre().trim(),
                                                    Integer.valueOf(peticion.getDatos_portal().getIdentificador().trim()),
                                                    peticion.getDatos_cliente().getNombre().trim(), peticion.getDatos_cliente().getApellido_paterno().trim(),
                                                    peticion.getDatos_cliente().getApellido_materno().trim(), peticion.getDatos_cliente().getReconocimiento_optico().trim(),
                                                    peticion.getDatos_cliente().getA_registro().trim(), peticion.getDatos_cliente().getA_emision().trim(),
                                                    peticion.getDatos_cliente().getClave_unica().trim(), peticion.getDatos_cliente().getN_emision().trim(),
                                                    peticion.getDatos_cliente().getClave_elector().trim(), peticion.getDatos_cliente().getCodigo_identificacion().trim(),
                                                    bitacora_identificador);

                                            componente.registrar_servicio(json.get("codigoRespuesta").toString(), json.get("descripcionRespuesta").toString(),
                                                    json.get("respuestaServicio").toString(), cuerpo, salida, solicitud_identificador, parametros.get(1));

                                            componente.actualizar_peticiones(0, 2);

                                            log.info("RESPUESTA: ");
                                            log.info(respuesta.toString());
                                            log.info("");
                                            log.info("");

                                            return new ResponseEntity(respuesta, HttpStatus.OK);

                                        } else if ((json.get("codigoRespuesta") + "").trim().equals("03") || (json.get("codigoRespuesta") + "").trim().equals("02")
                                                || (json.get("codigoRespuesta") + "").trim().equals("06")) {

                                            resultado.put("codigo", ConstantesFimpeINEUtilities.CODIGO_RESPUESTA_EXCEPCION);
                                            resultado.put("descripcion", ConstantesFimpeINEUtilities.DESCRIPCION_RESPUESTA_EXCEPCION);
                                            respuesta.put("resultado", resultado);

                                            int bitacora_identificador = componente.registrar_bitacora(convertir_salida(peticion), new JSONObject(respuesta).toString(), 200);

                                            int solicitud_identificador = componente.registrar_solicitud(peticion.getDatos_portal().getNombre().trim(),
                                                    Integer.valueOf(peticion.getDatos_portal().getIdentificador().trim()),
                                                    peticion.getDatos_cliente().getNombre().trim(), peticion.getDatos_cliente().getApellido_paterno().trim(),
                                                    peticion.getDatos_cliente().getApellido_materno().trim(), peticion.getDatos_cliente().getReconocimiento_optico().trim(),
                                                    peticion.getDatos_cliente().getA_registro().trim(), peticion.getDatos_cliente().getA_emision().trim(),
                                                    peticion.getDatos_cliente().getClave_unica().trim(), peticion.getDatos_cliente().getN_emision().trim(),
                                                    peticion.getDatos_cliente().getClave_elector().trim(), peticion.getDatos_cliente().getCodigo_identificacion().trim(),
                                                    bitacora_identificador);

                                            componente.registrar_servicio(json.get("codigoRespuesta").toString(), json.get("descripcionRespuesta").toString(),
                                                    json.get("respuestaServicio").toString(), cuerpo, salida, solicitud_identificador, parametros.get(1));

                                            componente.actualizar_peticiones(1, 2);

                                            log.info("RESPUESTA: ");
                                            log.info(respuesta.toString());
                                            log.info("");
                                            log.info("");

                                            return new ResponseEntity(respuesta, HttpStatus.CONFLICT);

                                        } else if ((json.get("codigoRespuesta") + "").trim().equals("01")) {

                                            resultado.put("codigo", json.get("codigoRespuesta").toString());
                                            resultado.put("descripcion", ConstantesFimpeINEUtilities.DESCRIPCION_RESPUESTA_EXCEPCION);
                                            respuesta.put("resultado", resultado);

                                            int bitacora_identificador = componente.registrar_bitacora(convertir_salida(peticion), new JSONObject(respuesta).toString(), 200);

                                            int solicitud_identificador = componente.registrar_solicitud(peticion.getDatos_portal().getNombre().trim(),
                                                    Integer.valueOf(peticion.getDatos_portal().getIdentificador().trim()),
                                                    peticion.getDatos_cliente().getNombre().trim(), peticion.getDatos_cliente().getApellido_paterno().trim(),
                                                    peticion.getDatos_cliente().getApellido_materno().trim(), peticion.getDatos_cliente().getReconocimiento_optico().trim(),
                                                    peticion.getDatos_cliente().getA_registro().trim(), peticion.getDatos_cliente().getA_emision().trim(),
                                                    peticion.getDatos_cliente().getClave_unica().trim(), peticion.getDatos_cliente().getN_emision().trim(),
                                                    peticion.getDatos_cliente().getClave_elector().trim(), peticion.getDatos_cliente().getCodigo_identificacion().trim(),
                                                    bitacora_identificador);

                                            componente.registrar_servicio(json.get("codigoRespuesta").toString(), json.get("descripcionRespuesta").toString(),
                                                    json.get("respuestaServicio").toString(), cuerpo, salida, solicitud_identificador, parametros.get(1));

                                            componente.actualizar_peticiones(1, 2);

                                            log.info("RESPUESTA: ");
                                            log.info(respuesta.toString());
                                            log.info("");
                                            log.info("");

                                            return new ResponseEntity(respuesta, HttpStatus.CONFLICT);

                                        } else if ((json.get("codigoRespuesta") + "").trim().equals("05")) {

                                            resultado.put("codigo", json.get("codigoRespuesta").toString());
                                            resultado.put("descripcion", "No se puede verificar tu identidad, intentalo nuevamente.");
                                            respuesta.put("resultado", resultado);

                                            int bitacora_identificador = componente.registrar_bitacora(convertir_salida(peticion), new JSONObject(respuesta).toString(), 200);

                                            int solicitud_identificador = componente.registrar_solicitud(peticion.getDatos_portal().getNombre().trim(),
                                                    Integer.valueOf(peticion.getDatos_portal().getIdentificador().trim()),
                                                    peticion.getDatos_cliente().getNombre().trim(), peticion.getDatos_cliente().getApellido_paterno().trim(),
                                                    peticion.getDatos_cliente().getApellido_materno().trim(), peticion.getDatos_cliente().getReconocimiento_optico().trim(),
                                                    peticion.getDatos_cliente().getA_registro().trim(), peticion.getDatos_cliente().getA_emision().trim(),
                                                    peticion.getDatos_cliente().getClave_unica().trim(), peticion.getDatos_cliente().getN_emision().trim(),
                                                    peticion.getDatos_cliente().getClave_elector().trim(), peticion.getDatos_cliente().getCodigo_identificacion().trim(),
                                                    bitacora_identificador);

                                            componente.registrar_servicio(json.get("codigoRespuesta").toString(), json.get("descripcionRespuesta").toString(),
                                                    json.get("respuestaServicio").toString(), cuerpo, salida, solicitud_identificador, parametros.get(1));

                                            componente.actualizar_peticiones(1, 2);

                                            log.info("RESPUESTA: ");
                                            log.info(respuesta.toString());
                                            log.info("");
                                            log.info("");

                                            return new ResponseEntity(respuesta, HttpStatus.CONFLICT);

                                        } else {

                                            resultado.put("codigo", json.get("codigoRespuesta").toString());
                                            resultado.put("descripcion", json.get("descripcionRespuesta").toString());
                                            respuesta.put("resultado", resultado);

                                            int bitacora_identificador = componente.registrar_bitacora(convertir_salida(peticion), new JSONObject(respuesta).toString(), 200);

                                            int solicitud_identificador = componente.registrar_solicitud(peticion.getDatos_portal().getNombre().trim(),
                                                    Integer.valueOf(peticion.getDatos_portal().getIdentificador().trim()),
                                                    peticion.getDatos_cliente().getNombre().trim(), peticion.getDatos_cliente().getApellido_paterno().trim(),
                                                    peticion.getDatos_cliente().getApellido_materno().trim(), peticion.getDatos_cliente().getReconocimiento_optico().trim(),
                                                    peticion.getDatos_cliente().getA_registro().trim(), peticion.getDatos_cliente().getA_emision().trim(),
                                                    peticion.getDatos_cliente().getClave_unica().trim(), peticion.getDatos_cliente().getN_emision().trim(),
                                                    peticion.getDatos_cliente().getClave_elector().trim(), peticion.getDatos_cliente().getCodigo_identificacion().trim(),
                                                    bitacora_identificador);

                                            componente.registrar_servicio(json.get("codigoRespuesta").toString(), json.get("descripcionRespuesta").toString(),
                                                    json.get("respuestaServicio").toString(), cuerpo, salida, solicitud_identificador, parametros.get(1));

                                            componente.actualizar_peticiones(1, 2);

                                            log.info("RESPUESTA: ");
                                            log.info(respuesta.toString());
                                            log.info("");
                                            log.info("");

                                            return new ResponseEntity(respuesta, HttpStatus.CONFLICT);

                                        }

                                    } else {

                                        resultado.put("codigo", ConstantesFimpeINEUtilities.CODIGO_RESPUESTA_FALLIDO);
                                        resultado.put("descripcion", ConstantesFimpeINEUtilities.DESCRIPCION_RESPUESTA_FALLIDO);
                                        respuesta.put("resultado", resultado);

                                        int bitacora_identificador = componente.registrar_bitacora(convertir_salida(peticion), new JSONObject(respuesta).toString(), 200);

                                        int solicitud_identificador = componente.registrar_solicitud(peticion.getDatos_portal().getNombre().trim(),
                                                Integer.valueOf(peticion.getDatos_portal().getIdentificador().trim()),
                                                peticion.getDatos_cliente().getNombre().trim(), peticion.getDatos_cliente().getApellido_paterno().trim(),
                                                peticion.getDatos_cliente().getApellido_materno().trim(), peticion.getDatos_cliente().getReconocimiento_optico().trim(),
                                                peticion.getDatos_cliente().getA_registro().trim(), peticion.getDatos_cliente().getA_emision().trim(),
                                                peticion.getDatos_cliente().getClave_unica().trim(), peticion.getDatos_cliente().getN_emision().trim(),
                                                peticion.getDatos_cliente().getClave_elector().trim(), peticion.getDatos_cliente().getCodigo_identificacion().trim(),
                                                bitacora_identificador);

                                        componente.registrar_servicio("-1", "VALOR CODIGORESPUESTA VACIO",
                                                "-1", cuerpo, salida, solicitud_identificador, parametros.get(1));

                                        componente.actualizar_peticiones(1, 2);

                                        log.info("RESPUESTA: ");
                                        log.info(respuesta.toString());
                                        log.info("");
                                        log.info("");

                                        return new ResponseEntity(respuesta, HttpStatus.CONFLICT);

                                    }

                                } else {

                                    resultado.put("codigo", ConstantesFimpeINEUtilities.CODIGO_RESPUESTA_FALLIDO);
                                    resultado.put("descripcion", ConstantesFimpeINEUtilities.DESCRIPCION_RESPUESTA_FALLIDO);
                                    respuesta.put("resultado", resultado);

                                    int bitacora_identificador = componente.registrar_bitacora(convertir_salida(peticion), new JSONObject(respuesta).toString(), 200);

                                    int solicitud_identificador = componente.registrar_solicitud(peticion.getDatos_portal().getNombre().trim(),
                                            Integer.valueOf(peticion.getDatos_portal().getIdentificador().trim()),
                                            peticion.getDatos_cliente().getNombre().trim(), peticion.getDatos_cliente().getApellido_paterno().trim(),
                                            peticion.getDatos_cliente().getApellido_materno().trim(), peticion.getDatos_cliente().getReconocimiento_optico().trim(),
                                            peticion.getDatos_cliente().getA_registro().trim(), peticion.getDatos_cliente().getA_emision().trim(),
                                            peticion.getDatos_cliente().getClave_unica().trim(), peticion.getDatos_cliente().getN_emision().trim(),
                                            peticion.getDatos_cliente().getClave_elector().trim(), peticion.getDatos_cliente().getCodigo_identificacion().trim(),
                                            bitacora_identificador);

                                    componente.registrar_servicio("-1", "ETIQUETA CODIGORESPUESTA NO ENCONTRADA",
                                            "-1", cuerpo, salida, solicitud_identificador, parametros.get(1));

                                    componente.actualizar_peticiones(1, 2);

                                    log.info("RESPUESTA: ");
                                    log.info(respuesta.toString());
                                    log.info("");
                                    log.info("");

                                    return new ResponseEntity(respuesta, HttpStatus.CONFLICT);

                                }

                            } else {

                                resultado.put("codigo", ConstantesFimpeINEUtilities.CODIGO_RESPUESTA_FALLIDO);
                                resultado.put("descripcion", ConstantesFimpeINEUtilities.DESCRIPCION_RESPUESTA_FALLIDO);
                                respuesta.put("resultado", resultado);

                                int bitacora_identificador = componente.registrar_bitacora(convertir_salida(peticion), new JSONObject(respuesta).toString(), 200);

                                int solicitud_identificador = componente.registrar_solicitud(peticion.getDatos_portal().getNombre().trim(),
                                        Integer.valueOf(peticion.getDatos_portal().getIdentificador().trim()),
                                        peticion.getDatos_cliente().getNombre().trim(), peticion.getDatos_cliente().getApellido_paterno().trim(),
                                        peticion.getDatos_cliente().getApellido_materno().trim(), peticion.getDatos_cliente().getReconocimiento_optico().trim(),
                                        peticion.getDatos_cliente().getA_registro().trim(), peticion.getDatos_cliente().getA_emision().trim(),
                                        peticion.getDatos_cliente().getClave_unica().trim(), peticion.getDatos_cliente().getN_emision().trim(),
                                        peticion.getDatos_cliente().getClave_elector().trim(), peticion.getDatos_cliente().getCodigo_identificacion().trim(),
                                        bitacora_identificador);

                                componente.registrar_servicio("-1", "ETIQUETA RETURN NO ENCONTRADA",
                                        "-1", cuerpo, salida, solicitud_identificador, parametros.get(1));

                                componente.actualizar_peticiones(1, 2);

                                log.info("RESPUESTA: ");
                                log.info(respuesta.toString());
                                log.info("");
                                log.info("");

                                return new ResponseEntity(respuesta, HttpStatus.CONFLICT);

                            }

                        } else {

                            resultado.put("codigo", ConstantesFimpeINEUtilities.CODIGO_RESPUESTA_FALLIDO);
                            resultado.put("descripcion", ConstantesFimpeINEUtilities.DESCRIPCION_RESPUESTA_FALLIDO);
                            respuesta.put("resultado", resultado);

                            int bitacora_identificador = componente.registrar_bitacora(convertir_salida(peticion), new JSONObject(respuesta).toString(), 200);

                            int solicitud_identificador = componente.registrar_solicitud(peticion.getDatos_portal().getNombre().trim(),
                                    Integer.valueOf(peticion.getDatos_portal().getIdentificador().trim()),
                                    peticion.getDatos_cliente().getNombre().trim(), peticion.getDatos_cliente().getApellido_paterno().trim(),
                                    peticion.getDatos_cliente().getApellido_materno().trim(), peticion.getDatos_cliente().getReconocimiento_optico().trim(),
                                    peticion.getDatos_cliente().getA_registro().trim(), peticion.getDatos_cliente().getA_emision().trim(),
                                    peticion.getDatos_cliente().getClave_unica().trim(), peticion.getDatos_cliente().getN_emision().trim(),
                                    peticion.getDatos_cliente().getClave_elector().trim(), peticion.getDatos_cliente().getCodigo_identificacion().trim(),
                                    bitacora_identificador);

                            componente.registrar_servicio("-1", "ETIQUETA VERIFICADATOSRESPONSE NO ENCONTRADA",
                                    "-1", cuerpo, salida, solicitud_identificador, parametros.get(1));

                            componente.actualizar_peticiones(1, 2);

                            log.info("RESPUESTA: ");
                            log.info(respuesta.toString());
                            log.info("");
                            log.info("");

                            return new ResponseEntity(respuesta, HttpStatus.CONFLICT);

                        }

                    } else {

                        resultado.put("codigo", ConstantesFimpeINEUtilities.CODIGO_RESPUESTA_FALLIDO);
                        resultado.put("descripcion", ConstantesFimpeINEUtilities.DESCRIPCION_RESPUESTA_FALLIDO);
                        respuesta.put("resultado", resultado);

                        int bitacora_identificador = componente.registrar_bitacora(convertir_salida(peticion), new JSONObject(respuesta).toString(), 200);

                        int solicitud_identificador = componente.registrar_solicitud(peticion.getDatos_portal().getNombre().trim(),
                                Integer.valueOf(peticion.getDatos_portal().getIdentificador().trim()),
                                peticion.getDatos_cliente().getNombre().trim(), peticion.getDatos_cliente().getApellido_paterno().trim(),
                                peticion.getDatos_cliente().getApellido_materno().trim(), peticion.getDatos_cliente().getReconocimiento_optico().trim(),
                                peticion.getDatos_cliente().getA_registro().trim(), peticion.getDatos_cliente().getA_emision().trim(),
                                peticion.getDatos_cliente().getClave_unica().trim(), peticion.getDatos_cliente().getN_emision().trim(),
                                peticion.getDatos_cliente().getClave_elector().trim(), peticion.getDatos_cliente().getCodigo_identificacion().trim(),
                                bitacora_identificador);

                        componente.registrar_servicio("-1", "ETIQUETA BODY NO ENCONTRADA",
                                "-1", cuerpo, salida, solicitud_identificador, parametros.get(1));

                        componente.actualizar_peticiones(1, 2);

                        log.info("RESPUESTA: ");
                        log.info(respuesta.toString());
                        log.info("");
                        log.info("");

                        return new ResponseEntity(respuesta, HttpStatus.CONFLICT);

                    }

                } else {

                    resultado.put("codigo", ConstantesFimpeINEUtilities.CODIGO_RESPUESTA_FALLIDO);
                    resultado.put("descripcion", ConstantesFimpeINEUtilities.DESCRIPCION_RESPUESTA_FALLIDO);
                    respuesta.put("resultado", resultado);

                    int bitacora_identificador = componente.registrar_bitacora(convertir_salida(peticion), new JSONObject(respuesta).toString(), 200);

                    int solicitud_identificador = componente.registrar_solicitud(peticion.getDatos_portal().getNombre().trim(),
                            Integer.valueOf(peticion.getDatos_portal().getIdentificador().trim()),
                            peticion.getDatos_cliente().getNombre().trim(), peticion.getDatos_cliente().getApellido_paterno().trim(),
                            peticion.getDatos_cliente().getApellido_materno().trim(), peticion.getDatos_cliente().getReconocimiento_optico().trim(),
                            peticion.getDatos_cliente().getA_registro().trim(), peticion.getDatos_cliente().getA_emision().trim(),
                            peticion.getDatos_cliente().getClave_unica().trim(), peticion.getDatos_cliente().getN_emision().trim(),
                            peticion.getDatos_cliente().getClave_elector().trim(), peticion.getDatos_cliente().getCodigo_identificacion().trim(),
                            bitacora_identificador);

                    componente.registrar_servicio("-1", "ETIQUETA ENVELOPE NO ENCONTRADA",
                            "-1", cuerpo, salida, solicitud_identificador, parametros.get(1));

                    componente.actualizar_peticiones(1, 2);

                    log.info("RESPUESTA: ");
                    log.info(respuesta.toString());
                    log.info("");
                    log.info("");

                    return new ResponseEntity(respuesta, HttpStatus.CONFLICT);

                }

            } else {

                resultado.put("codigo", ConstantesFimpeINEUtilities.CODIGO_RESPUESTA_EXCEPCION);
                resultado.put("descripcion", ConstantesFimpeINEUtilities.DESCRIPCION_RESPUESTA_EXCEPCION);
                respuesta.put("resultado", resultado);

                int bitacora_identificador = componente.registrar_bitacora(convertir_salida(peticion), new JSONObject(respuesta).toString(), 200);

                int solicitud_identificador = componente.registrar_solicitud(peticion.getDatos_portal().getNombre().trim(),
                        Integer.valueOf(peticion.getDatos_portal().getIdentificador().trim()),
                        peticion.getDatos_cliente().getNombre().trim(), peticion.getDatos_cliente().getApellido_paterno().trim(),
                        peticion.getDatos_cliente().getApellido_materno().trim(), peticion.getDatos_cliente().getReconocimiento_optico().trim(),
                        peticion.getDatos_cliente().getA_registro().trim(), peticion.getDatos_cliente().getA_emision().trim(),
                        peticion.getDatos_cliente().getClave_unica().trim(), peticion.getDatos_cliente().getN_emision().trim(),
                        peticion.getDatos_cliente().getClave_elector().trim(), peticion.getDatos_cliente().getCodigo_identificacion().trim(),
                        bitacora_identificador);

                componente.registrar_servicio("-1", "CODIGO DIFERENTE DE 200",
                        "-1", cuerpo, salida, solicitud_identificador, parametros.get(1));

                componente.actualizar_peticiones(1, 2);

                log.info("RESPUESTA: ");
                log.info(respuesta.toString());
                log.info("");
                log.info("");

                return new ResponseEntity(respuesta, HttpStatus.CONFLICT);

            }

        } catch (Exception e) {

            resultado.put("codigo", ConstantesFimpeINEUtilities.CODIGO_RESPUESTA_EXCEPCION);
            resultado.put("descripcion", ConstantesFimpeINEUtilities.DESCRIPCION_RESPUESTA_EXCEPCION);
            respuesta.put("resultado", resultado);

            int bitacora_identificador = componente.registrar_bitacora(convertir_salida(peticion), new JSONObject(respuesta).toString(), 200);

            int solicitud_identificador = componente.registrar_solicitud(peticion.getDatos_portal().getNombre().trim(),
                    Integer.valueOf(peticion.getDatos_portal().getIdentificador().trim()),
                    peticion.getDatos_cliente().getNombre().trim(), peticion.getDatos_cliente().getApellido_paterno().trim(),
                    peticion.getDatos_cliente().getApellido_materno().trim(), peticion.getDatos_cliente().getReconocimiento_optico().trim(),
                    peticion.getDatos_cliente().getA_registro().trim(), peticion.getDatos_cliente().getA_emision().trim(),
                    peticion.getDatos_cliente().getClave_unica().trim(), peticion.getDatos_cliente().getN_emision().trim(),
                    peticion.getDatos_cliente().getClave_elector().trim(), peticion.getDatos_cliente().getCodigo_identificacion().trim(),
                    bitacora_identificador);

            componente.registrar_servicio("-2", "EXCEPCION",
                    "-2", cuerpo, e.getMessage(), solicitud_identificador, parametros.get(1));

            componente.actualizar_peticiones(1, 2);

            log.error("EXCEPCION AL EJECUTAR SERVICIO DE FIMPE");
            log.error(e.getMessage());
            log.error("");
            log.error("");

            log.error("RESPUESTA: ");
            log.error(respuesta.toString());
            log.error("");
            log.error("");

            return new ResponseEntity(respuesta, HttpStatus.CONFLICT);

        }

    }

    private List<String> solicitud(RequestIdentificacionModel solicitud) {

        List<String> parametros = new ArrayList<>();
        JSONObject datos_cliente = new JSONObject();

        datos_cliente.put("nombre", solicitud.getDatos_cliente().getNombre());
        datos_cliente.put("apellidoPaterno", solicitud.getDatos_cliente().getApellido_paterno());
        datos_cliente.put("apellidoMaterno", solicitud.getDatos_cliente().getApellido_materno());
        datos_cliente.put("ocr", solicitud.getDatos_cliente().getReconocimiento_optico());
        datos_cliente.put("anioRegistro", solicitud.getDatos_cliente().getA_registro());
        datos_cliente.put("anioEmision", solicitud.getDatos_cliente().getA_emision());
        datos_cliente.put("consentimiento", PropiedadesFimpeINEUtilities.CONSENTIMIENTO_IDENTIFICACION);
        datos_cliente.put("numeroEmision", solicitud.getDatos_cliente().getN_emision());
        datos_cliente.put("claveElector", solicitud.getDatos_cliente().getClave_elector());
        datos_cliente.put("cic", solicitud.getDatos_cliente().getCodigo_identificacion());
        datos_cliente.put("curp", solicitud.getDatos_cliente().getClave_unica());

        JSONObject datos_institucion = new JSONObject();
        LocalDateTime tiempo = LocalDateTime.now();

        String referencia = "F-" + tiempo.getHour() + tiempo.getMinute() + tiempo.getSecond() + tiempo.getNano();

        datos_institucion.put("idInstitucion", PropiedadesFimpeINEUtilities.INSTITUCION_IDENTIFICACION);
        datos_institucion.put("idEstacion", PropiedadesFimpeINEUtilities.ESTACION_IDENTIFICACION);
        datos_institucion.put("fechaHora", PropiedadesFimpeINEUtilities.FECHA_IDENTIFICACION);
        datos_institucion.put("referencia", referencia);
        datos_institucion.put("latitud", PropiedadesFimpeINEUtilities.LATITUD_IDENTIFICACION);
        datos_institucion.put("longitud", PropiedadesFimpeINEUtilities.LONGITUD_IDENTIFICACION);
        datos_institucion.put("codigoPostal", PropiedadesFimpeINEUtilities.POSTAL_IDENTIFICACION);
        datos_institucion.put("ciudad", PropiedadesFimpeINEUtilities.CIUDAD_IDENTIFICACION);
        datos_institucion.put("estado", PropiedadesFimpeINEUtilities.ESTADO_IDENTIFICACION);

        JSONObject datos_mafi = new JSONObject();

        datos_mafi.put("nombreMAFI", PropiedadesFimpeINEUtilities.MAFI_IDENTIFICACION);
        datos_mafi.put("versionMAFI", PropiedadesFimpeINEUtilities.VERSION_IDENTIFICACION);
        datos_mafi.put("tipoLector", PropiedadesFimpeINEUtilities.TIPO_IDENTIFICACION);
        datos_mafi.put("idParametros", Integer.parseInt(PropiedadesFimpeINEUtilities.PARAMETROS_IDENTIFICACION));
        datos_mafi.put("tiempoLectura", Integer.parseInt(PropiedadesFimpeINEUtilities.TIEMPO_IDENTIFICACION));
        datos_mafi.put("intentosLectura", Integer.parseInt(PropiedadesFimpeINEUtilities.INTENTOS_IDENTIFICACION));

        JSONObject cuerpo = new JSONObject();

        cuerpo.put("versionJSON", PropiedadesFimpeINEUtilities.SOLICITUD_IDENTIFICACION);
        cuerpo.put("datosCliente", datos_cliente);
        cuerpo.put("datosInstitucion", datos_institucion);
        cuerpo.put("datosMAFI", datos_mafi);

        parametros.add(cuerpo.toString());
        parametros.add(referencia);

        return parametros;

    }

    private String solicitud_sin_rostro(RequestIdentificacionModel solicitud) {

        JSONObject datos_cliente = new JSONObject();

        datos_cliente.put("nombre", solicitud.getDatos_cliente().getNombre());
        datos_cliente.put("apellidoPaterno", solicitud.getDatos_cliente().getApellido_paterno());
        datos_cliente.put("apellidoMaterno", solicitud.getDatos_cliente().getApellido_materno());
        datos_cliente.put("ocr", solicitud.getDatos_cliente().getReconocimiento_optico());
        datos_cliente.put("anioRegistro", solicitud.getDatos_cliente().getA_registro());
        datos_cliente.put("anioEmision", solicitud.getDatos_cliente().getA_emision());
        datos_cliente.put("consentimiento", PropiedadesFimpeINEUtilities.CONSENTIMIENTO_IDENTIFICACION);
        datos_cliente.put("numeroEmision", solicitud.getDatos_cliente().getN_emision());
        datos_cliente.put("claveElector", solicitud.getDatos_cliente().getClave_elector());
        datos_cliente.put("cic", solicitud.getDatos_cliente().getCodigo_identificacion());
        datos_cliente.put("curp", solicitud.getDatos_cliente().getClave_unica());

        JSONObject datos_institucion = new JSONObject();
        LocalDateTime tiempo = LocalDateTime.now();

        datos_institucion.put("idInstitucion", PropiedadesFimpeINEUtilities.INSTITUCION_IDENTIFICACION);
        datos_institucion.put("idEstacion", PropiedadesFimpeINEUtilities.ESTACION_IDENTIFICACION);
        datos_institucion.put("fechaHora", PropiedadesFimpeINEUtilities.FECHA_IDENTIFICACION);
        datos_institucion.put("referencia", "F-" + tiempo.getHour() + tiempo.getMinute() + tiempo.getSecond() + tiempo.getNano());
        datos_institucion.put("latitud", PropiedadesFimpeINEUtilities.LATITUD_IDENTIFICACION);
        datos_institucion.put("longitud", PropiedadesFimpeINEUtilities.LONGITUD_IDENTIFICACION);
        datos_institucion.put("codigoPostal", PropiedadesFimpeINEUtilities.POSTAL_IDENTIFICACION);
        datos_institucion.put("ciudad", PropiedadesFimpeINEUtilities.CIUDAD_IDENTIFICACION);
        datos_institucion.put("estado", PropiedadesFimpeINEUtilities.ESTADO_IDENTIFICACION);

        JSONObject datos_mafi = new JSONObject();

        datos_mafi.put("nombreMAFI", PropiedadesFimpeINEUtilities.MAFI_IDENTIFICACION);
        datos_mafi.put("versionMAFI", PropiedadesFimpeINEUtilities.VERSION_IDENTIFICACION);
        datos_mafi.put("tipoLector", PropiedadesFimpeINEUtilities.TIPO_IDENTIFICACION);
        datos_mafi.put("idParametros", Integer.parseInt(PropiedadesFimpeINEUtilities.PARAMETROS_IDENTIFICACION));
        datos_mafi.put("tiempoLectura", Integer.parseInt(PropiedadesFimpeINEUtilities.TIEMPO_IDENTIFICACION));
        datos_mafi.put("intentosLectura", Integer.parseInt(PropiedadesFimpeINEUtilities.INTENTOS_IDENTIFICACION));

        JSONObject cuerpo = new JSONObject();

        cuerpo.put("versionJSON", PropiedadesFimpeINEUtilities.SOLICITUD_IDENTIFICACION);
        cuerpo.put("datosCliente", datos_cliente);
        cuerpo.put("datosInstitucion", datos_institucion);
        cuerpo.put("datosMAFI", datos_mafi);

        return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"services.middleware.fimpe.org.mx\">\n"
                + "   <soapenv:Header/>\n"
                + "   <soapenv:Body>\n"
                + "      <ser:verificaDatos>\n"
                + "         <arg0>" + cuerpo.toString() + "</arg0>\n"
                + "      </ser:verificaDatos>\n"
                + "   </soapenv:Body>\n"
                + "</soapenv:Envelope>";

    }

    private String convertir_salida(RequestIdentificacionModel solicitud) {

        try {

            ObjectMapper mapa = new ObjectMapper();
            return mapa.writeValueAsString(solicitud);

        } catch (Exception e) {

            log.error("EXCEPCION AL REALIZAR LA CONVERSION DE SALIDA");
            log.error(e.getMessage());
            log.error("");
            log.error("");

            return e.getMessage();

        }

    }

}
