package com.fincomun.validadorcurp.service;

import org.json.XML;
import java.util.List;
import java.util.HashMap;
import java.util.Objects;
import org.json.JSONObject;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.StringEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import com.fincomun.validadorcurp.utilities.PropiedadesUtilities;
import com.fincomun.validadorcurp.utilities.ValidarClaveUtilities;
import com.fincomun.validadorcurp.component.ConsultasClaveComponent;
import com.fincomun.validadorcurp.utilities.ConstantesClaveUtilities;

@Slf4j
@Service
public class ValidarClaveService {

    @Autowired
    private ValidarClaveUtilities utilidades;

    @Autowired
    private ConsultasClaveComponent componente;

    public ResponseEntity<Object> servicio(String solicitud) {

        log.info("=============== ValidarClaveService ===============");
        log.info("");
        log.info("");

        HashMap<String, String> resultado = new LinkedHashMap<>();
        HashMap<String, Object> respuesta = new LinkedHashMap<>();

        List<Integer> lista = componente.obtener_configuracion(1, "RENAPO", 1);

        log.info("Lista");
        log.info(lista.toString());

        Integer validacion = utilidades.validar_peticiones(lista.get(1), lista.get(2));

        if (validacion != 0) {

            resultado.put("codigo", "150");
            resultado.put("descripcion", "Sin peticiones.");
            respuesta.put("resultado", resultado);

            componente.registrar_bitacora_renapo(" ", new JSONObject(respuesta).toString(), 100);

            return new ResponseEntity(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        if (Objects.isNull(solicitud)) {

            resultado.put("codigo", ConstantesClaveUtilities.CODIGO_PETICION_VACIA);
            resultado.put("descripcion", ConstantesClaveUtilities.DESCRIPCION_PETICION_VACIA);
            respuesta.put("resultado", resultado);

            componente.registrar_bitacora_renapo(" ", new JSONObject(respuesta).toString(), 100);

            return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);

        }

        JSONObject peticion = new JSONObject(solicitud);

        int resultado_proceso = utilidades.validar_proceso(peticion);

        if (resultado_proceso != 10) {

            switch (resultado_proceso) {

                case 1:
                    resultado.put("codigo", ConstantesClaveUtilities.CODIGO_CAMPO_PROCESO);
                    resultado.put("descripcion", ConstantesClaveUtilities.DESCRIPCION_CAMPO_PROCESO);
                    respuesta.put("resultado", resultado);
                    break;

                case 2:
                    resultado.put("codigo", ConstantesClaveUtilities.CODIGO_VALOR_PROCESO);
                    resultado.put("descripcion", ConstantesClaveUtilities.DESCRIPCION_VALOR_PROCESO);
                    respuesta.put("resultado", resultado);
                    break;

                case 3:
                    resultado.put("codigo", ConstantesClaveUtilities.CODIGO_PROCESO_NUMERICO);
                    resultado.put("descripcion", ConstantesClaveUtilities.DESCRIPCION_PROCESO_NUMERICO);
                    respuesta.put("resultado", resultado);
                    break;

                case 4:
                    resultado.put("codigo", ConstantesClaveUtilities.CODIGO_PROCESO_VALIDO);
                    resultado.put("descripcion", ConstantesClaveUtilities.DESCRIPCION_PROCESO_VALIDO);
                    respuesta.put("resultado", resultado);
                    break;

            }

            componente.registrar_bitacora_renapo(solicitud, new JSONObject(respuesta).toString(), 100);

            return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);

        }

        Integer resultado_portal = utilidades.validar_portal(peticion);

        if (resultado_portal != 0) {

            switch (resultado_portal) {

                case 1:
                    resultado.put("codigo", ConstantesClaveUtilities.CODIGO_INFORMACION_PORTAL);
                    resultado.put("descripcion", ConstantesClaveUtilities.DESCRIPCION_INFORMACION_PORTAL);
                    respuesta.put("resultado", resultado);
                    break;

                case 2:
                    resultado.put("codigo", ConstantesClaveUtilities.CODIGO_CAMPO_IDENTIFICADOR_PORTAL);
                    resultado.put("descripcion", ConstantesClaveUtilities.DESCRIPCION_CAMPO_IDENTIFICADOR_PORTAL);
                    respuesta.put("resultado", resultado);
                    break;

                case 3:
                    resultado.put("codigo", ConstantesClaveUtilities.CODIGO_VALOR_IDENTIFICADOR_PORTAL);
                    resultado.put("descripcion", ConstantesClaveUtilities.DESCRIPCION_VALOR_IDENTIFICADOR_PORTAL);
                    respuesta.put("resultado", resultado);
                    break;

                case 4:
                    resultado.put("codigo", ConstantesClaveUtilities.CODIGO_VALOR_IDENTIFICADOR_PORTAL_NUMERICO);
                    resultado.put("descripcion", ConstantesClaveUtilities.DESCRIPCION_VALOR_IDENTIFICADOR_PORTAL_NUMERICO);
                    respuesta.put("resultado", resultado);
                    break;

                case 5:
                    resultado.put("codigo", ConstantesClaveUtilities.CODIGO_CAMPO_NOMBRE_PORTAL);
                    resultado.put("descripcion", ConstantesClaveUtilities.DESCRIPCION_CAMPO_NOMBRE_PORTAL);
                    respuesta.put("resultado", resultado);
                    break;

                case 6:
                    resultado.put("codigo", ConstantesClaveUtilities.CODIGO_VALOR_NOMBRE_PORTAL);
                    resultado.put("descripcion", ConstantesClaveUtilities.DESCRIPCION_VALOR_NOMBRE_PORTAL);
                    respuesta.put("resultado", resultado);
                    break;

            }

            componente.registrar_bitacora_renapo(solicitud, new JSONObject(respuesta).toString(), 100);

            return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);

        }

        JSONObject portal = new JSONObject(peticion.get("datos_portal").toString());

        Integer codigo = componente.portal_valido(Integer.parseInt((portal.get("identificador") + "").trim()), (portal.get("nombre") + "").trim());

        if (codigo != 0) {

            HttpStatus estatus = HttpStatus.BAD_REQUEST;

            switch (codigo) {

                case 1:
                    resultado.put("codigo", ConstantesClaveUtilities.CODIGO_PORTAL_INACTIVO);
                    resultado.put("descripcion", ConstantesClaveUtilities.DESCRIPCION_PORTAL_INACTIVO);
                    respuesta.put("resultado", resultado);
                    break;
                case 2:
                    resultado.put("codigo", ConstantesClaveUtilities.CODIGO_PORTAL_INCORRECTO);
                    resultado.put("descripcion", ConstantesClaveUtilities.DESCRIPCION_PORTAL_INCORRECTO);
                    respuesta.put("resultado", resultado);
                    break;
                case 3:
                    resultado.put("codigo", ConstantesClaveUtilities.CODIGO_ABRIR_CONEXION_FALLIDO);
                    resultado.put("descripcion", ConstantesClaveUtilities.DESCRIPCION_ABRIR_CONEXION_FALLIDO);
                    respuesta.put("resultado", resultado);

                    estatus = HttpStatus.SERVICE_UNAVAILABLE;
                    break;
                case 4:
                    resultado.put("codigo", ConstantesClaveUtilities.CODIGO_CONSULTAR_FALLIDO);
                    resultado.put("descripcion", ConstantesClaveUtilities.DESCRIPCION_CONSULTAR_FALLIDO);
                    respuesta.put("resultado", resultado);

                    estatus = HttpStatus.SERVICE_UNAVAILABLE;
                    break;
                case 5:
                    resultado.put("codigo", ConstantesClaveUtilities.CODIGO_CERRAR_CONEXION_FALLIDO);
                    resultado.put("descripcion", ConstantesClaveUtilities.DESCRIPCION_CERRAR_CONEXION_FALLIDO);
                    respuesta.put("resultado", resultado);

                    estatus = HttpStatus.SERVICE_UNAVAILABLE;
                    break;

            }

            componente.registrar_bitacora_renapo(solicitud, new JSONObject(respuesta).toString(), 100);

            return new ResponseEntity(respuesta, estatus);

        }

        Integer resultado_cliente = utilidades.validar_cliente(peticion);

        if (resultado_cliente != 0) {

            switch (resultado_cliente) {

                case 1:
                    resultado.put("codigo", ConstantesClaveUtilities.CODIGO_INFORMACION_CLIENTE);
                    resultado.put("descripcion", ConstantesClaveUtilities.DESCRIPCION_INFORMACION_CLIENTE);
                    respuesta.put("resultado", resultado);
                    break;

                case 2:
                    resultado.put("codigo", ConstantesClaveUtilities.CODIGO_CAMPO_CLAVE);
                    resultado.put("descripcion", ConstantesClaveUtilities.DESCRIPCION_CAMPO_CLAVE);
                    respuesta.put("resultado", resultado);
                    break;

                case 3:
                    resultado.put("codigo", ConstantesClaveUtilities.CODIGO_VALOR_CLAVE);
                    resultado.put("descripcion", ConstantesClaveUtilities.DESCRIPCION_VALOR_CLAVE);
                    respuesta.put("resultado", resultado);
                    break;

            }

            componente.registrar_bitacora_renapo(solicitud, new JSONObject(respuesta).toString(), 100);

            return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);

        }

        return servicio_validar(peticion);

    }

    private ResponseEntity<Object> servicio_validar(JSONObject solicitud) {

        HashMap<String, String> resultado = new LinkedHashMap<>();
        HashMap<String, Object> respuesta = new LinkedHashMap<>();

        String peticion = solicitud_validar(solicitud);

        JSONObject datos_cliente = new JSONObject(solicitud.get("datos_cliente").toString());
        JSONObject datos_portal = new JSONObject(solicitud.get("datos_portal").toString());

        RequestConfig configuracion = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(5000).setSocketTimeout(5000).build();
        HttpClient cliente = HttpClientBuilder.create().setDefaultRequestConfig(configuracion).build();

        HttpPost operacion = new HttpPost(PropiedadesUtilities.DIRECCION_CLAVE);
        operacion.addHeader("Content-Type", PropiedadesUtilities.CABECERA_CLAVE);

        String cuerpo = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"services.svbi.fimpe.org.mx\">\n"
                + "   <soapenv:Header/>\n"
                + "   <soapenv:Body>\n"
                + "      <ser:verificaDatos>\n"
                + "         <arg0>" + peticion + "</arg0>\n"
                + "      </ser:verificaDatos>\n"
                + "   </soapenv:Body>\n"
                + "</soapenv:Envelope>";

        log.info("Entrada Servicio: ");
        log.info(cuerpo);
        log.info("");
        log.info("");

        try {

            StringEntity entidad = new StringEntity(cuerpo);

            operacion.setEntity(entidad);

            HttpResponse servicio = cliente.execute(operacion);

            String salida = EntityUtils.toString(servicio.getEntity());

            if (servicio.getStatusLine().getStatusCode() == 200) {

                log.info("Salida Servicio: ");
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

                                            if (json.has("respuestaRENAPO")) {

                                                JSONObject registro = new JSONObject(json.get("respuestaRENAPO").toString());

                                                if (registro.has("CURPStatus")) {

                                                    JSONObject estatus = new JSONObject(registro.get("CURPStatus").toString());

                                                    if (estatus.has("tipoError") && estatus.has("codigoError")) {

                                                        if ((estatus.get("tipoError") + "").isEmpty() && (estatus.get("codigoError") + "").isEmpty()) {

                                                            if (estatus.has("resultCURPS")) {

                                                                JSONObject resultados = new JSONObject(estatus.get("resultCURPS").toString());

                                                                if (resultados.has("numEntidadReg") && resultados.has("apellidoPaterno")
                                                                        && resultados.has("libro") && resultados.has("statusCurp")
                                                                        && resultados.has("cveEntidadNac") && resultados.has("numActa")
                                                                        && resultados.has("CRIP") && resultados.has("tomo")
                                                                        && resultados.has("cveEntidadEmisora") && resultados.has("FolioCertificado")
                                                                        && resultados.has("anioReg") && resultados.has("cveMunicipioReg")
                                                                        && resultados.has("FolioCarta") && resultados.has("CURP")
                                                                        && resultados.has("apellidoMaterno") && resultados.has("nombres")
                                                                        && resultados.has("nacionalidad") && resultados.has("foja")
                                                                        && resultados.has("NumRegExtranjeros") && resultados.has("fechNac")
                                                                        && resultados.has("sexo") && resultados.has("docProbatorio")) {

                                                                    HashMap<String, Object> informacion = new LinkedHashMap<>();

                                                                    informacion.put("n_entidad_registrada", resultados.get("numEntidadReg") + "");
                                                                    informacion.put("a_paterno", resultados.get("apellidoPaterno") + "");
                                                                    informacion.put("libro", resultados.get("libro") + "");
                                                                    informacion.put("estatus_clave", resultados.get("statusCurp") + "");
                                                                    informacion.put("c_entidad_nacimiento", resultados.get("cveEntidadNac") + "");
                                                                    informacion.put("numero_acta", resultados.get("numActa") + "");
                                                                    informacion.put("c_registro_identificacion", resultados.get("CRIP") + "");
                                                                    informacion.put("tomo", resultados.get("tomo") + "");
                                                                    informacion.put("c_entidad_emisora", resultados.get("cveEntidadEmisora") + "");
                                                                    informacion.put("folio_certificado", resultados.get("FolioCertificado") + "");
                                                                    informacion.put("a_registro", resultados.get("anioReg") + "");
                                                                    informacion.put("c_municipio_registro", resultados.get("cveMunicipioReg") + "");
                                                                    informacion.put("folio_carta", resultados.get("FolioCarta") + "");
                                                                    informacion.put("clave_unica", resultados.get("CURP") + "");
                                                                    informacion.put("a_materno", resultados.get("apellidoMaterno") + "");
                                                                    informacion.put("nombres", resultados.get("nombres") + "");
                                                                    informacion.put("nacionalidad", resultados.get("nacionalidad") + "");
                                                                    informacion.put("foja", resultados.get("foja") + "");
                                                                    informacion.put("n_registro_extranjeros", resultados.get("NumRegExtranjeros") + "");
                                                                    informacion.put("fecha_nacimiento", resultados.get("fechNac") + "");
                                                                    informacion.put("sexo", resultados.get("sexo") + "");
                                                                    informacion.put("documento_probatorio", resultados.get("docProbatorio") + "");

                                                                    resultado.put("codigo", ConstantesClaveUtilities.CODIGO_PETICION_EXITOSA);
                                                                    resultado.put("descripcion", ConstantesClaveUtilities.DESCRIPCION_PETICION_EXITOSA);
                                                                    respuesta.put("resultado", resultado);
                                                                    respuesta.put("informacion", informacion);

                                                                    int bitacora_identificador = componente.registrar_bitacora_renapo(solicitud.toString(), new JSONObject(respuesta).toString(), 300);

                                                                    int solicitud_identificador = componente.registrar_solicitud_renapo(datos_portal.get("nombre") + "",
                                                                            Integer.parseInt(datos_portal.get("identificador") + ""),
                                                                            datos_cliente.get("clave_unica") + "",
                                                                            bitacora_identificador);

                                                                    componente.registrar_servicio_renapo(json.get("codigoRespuesta").toString(), json.get("descripcionRespuesta").toString(),
                                                                            json.get("referencia").toString(), cuerpo, salida, solicitud_identificador);

                                                                    componente.actualizar_peticiones(0, 1);

                                                                    log.info("Respuesta: ");
                                                                    log.info(respuesta.toString());
                                                                    log.info("");
                                                                    log.info("");

                                                                    return new ResponseEntity(respuesta, HttpStatus.OK);

                                                                } else {

                                                                    resultado.put("codigo", ConstantesClaveUtilities.CODIGO_RESPUESTA_FALLIDO);
                                                                    resultado.put("descripcion", ConstantesClaveUtilities.DESCRIPCION_RESPUESTA_FALLIDO);
                                                                    respuesta.put("resultado", resultado);

                                                                    int bitacora_identificador = componente.registrar_bitacora_renapo(solicitud.toString(), new JSONObject(respuesta).toString(), 200);

                                                                    int solicitud_identificador = componente.registrar_solicitud_renapo(datos_portal.get("nombre") + "",
                                                                            Integer.parseInt(datos_portal.get("identificador") + ""),
                                                                            datos_cliente.get("clave_unica") + "",
                                                                            bitacora_identificador);

                                                                    //Modificacion Excepciones
                                                                    componente.registrar_servicio_renapo("-1", "ETIQUETAS DE LOS RESULTADOS NO ENCONTRADAS",
                                                                            "-1", cuerpo, salida, solicitud_identificador);

                                                                    componente.actualizar_peticiones(1, 1);

                                                                    log.info("Respuesta: ");
                                                                    log.info(respuesta.toString());
                                                                    log.info("");
                                                                    log.info("");

                                                                    return new ResponseEntity(respuesta, HttpStatus.CONFLICT);

                                                                }

                                                            } else {

                                                                resultado.put("codigo", ConstantesClaveUtilities.CODIGO_RESPUESTA_FALLIDO);
                                                                resultado.put("descripcion", ConstantesClaveUtilities.DESCRIPCION_RESPUESTA_FALLIDO);
                                                                respuesta.put("resultado", resultado);

                                                                int bitacora_identificador = componente.registrar_bitacora_renapo(solicitud.toString(), new JSONObject(respuesta).toString(), 200);

                                                                int solicitud_identificador = componente.registrar_solicitud_renapo(datos_portal.get("nombre") + "",
                                                                        Integer.parseInt(datos_portal.get("identificador") + ""),
                                                                        datos_cliente.get("clave_unica") + "",
                                                                        bitacora_identificador);

                                                                //Modificacion Excepciones
                                                                componente.registrar_servicio_renapo("-1", "ETIQUETA RESULTSCURP NO ENCONTRADA",
                                                                        "-1", cuerpo, salida, solicitud_identificador);

                                                                componente.actualizar_peticiones(1, 1);

                                                                log.info("Respuesta: ");
                                                                log.info(respuesta.toString());
                                                                log.info("");
                                                                log.info("");

                                                                return new ResponseEntity(respuesta, HttpStatus.CONFLICT);

                                                            }

                                                        } else {

                                                            resultado.put("codigo", ConstantesClaveUtilities.CODIGO_RESPUESTA_FALLIDO);
                                                            resultado.put("descripcion", ConstantesClaveUtilities.DESCRIPCION_RESPUESTA_FALLIDO);
                                                            respuesta.put("resultado", resultado);

                                                            int bitacora_identificador = componente.registrar_bitacora_renapo(solicitud.toString(), new JSONObject(respuesta).toString(), 200);

                                                            int solicitud_identificador = componente.registrar_solicitud_renapo(datos_portal.get("nombre") + "",
                                                                    Integer.parseInt(datos_portal.get("identificador") + ""),
                                                                    datos_cliente.get("clave_unica") + "",
                                                                    bitacora_identificador);

                                                            //Modificacion Excepciones
                                                            componente.registrar_servicio_renapo("-1", "CAMPO TIPOERROR / CODIGOERROR VACIO",
                                                                    "-1", cuerpo, salida, solicitud_identificador);

                                                            componente.actualizar_peticiones(1, 1);

                                                            log.info("Respuesta: ");
                                                            log.info(respuesta.toString());
                                                            log.info("");
                                                            log.info("");

                                                            return new ResponseEntity(respuesta, HttpStatus.CONFLICT);

                                                        }

                                                    } else {

                                                        resultado.put("codigo", ConstantesClaveUtilities.CODIGO_RESPUESTA_FALLIDO);
                                                        resultado.put("descripcion", ConstantesClaveUtilities.DESCRIPCION_RESPUESTA_FALLIDO);
                                                        respuesta.put("resultado", resultado);

                                                        int bitacora_identificador = componente.registrar_bitacora_renapo(solicitud.toString(), new JSONObject(respuesta).toString(), 200);

                                                        int solicitud_identificador = componente.registrar_solicitud_renapo(datos_portal.get("nombre") + "",
                                                                Integer.parseInt(datos_portal.get("identificador") + ""),
                                                                datos_cliente.get("clave_unica") + "",
                                                                bitacora_identificador);

                                                        //Modificacion Excepciones
                                                        componente.registrar_servicio_renapo("-1", "ETIQUETA TIPOERROR / CODIGOERROR NO ENCONTRADA",
                                                                "-1", cuerpo, salida, solicitud_identificador);

                                                        componente.actualizar_peticiones(1, 1);

                                                        log.info("Respuesta: ");
                                                        log.info(respuesta.toString());
                                                        log.info("");
                                                        log.info("");

                                                        return new ResponseEntity(respuesta, HttpStatus.CONFLICT);

                                                    }

                                                } else {

                                                    resultado.put("codigo", ConstantesClaveUtilities.CODIGO_RESPUESTA_FALLIDO);
                                                    resultado.put("descripcion", ConstantesClaveUtilities.DESCRIPCION_RESPUESTA_FALLIDO);
                                                    respuesta.put("resultado", resultado);

                                                    int bitacora_identificador = componente.registrar_bitacora_renapo(solicitud.toString(), new JSONObject(respuesta).toString(), 200);

                                                    int solicitud_identificador = componente.registrar_solicitud_renapo(datos_portal.get("nombre") + "",
                                                            Integer.parseInt(datos_portal.get("identificador") + ""),
                                                            datos_cliente.get("clave_unica") + "",
                                                            bitacora_identificador);

                                                    //Modificacion Excepciones
                                                    componente.registrar_servicio_renapo("-1", "ETIQUETA CURPSTATUS NO ENCONTRADA",
                                                            "-1", cuerpo, salida, solicitud_identificador);

                                                    componente.actualizar_peticiones(1, 1);

                                                    log.info("Respuesta: ");
                                                    log.info(respuesta.toString());
                                                    log.info("");
                                                    log.info("");

                                                    return new ResponseEntity(respuesta, HttpStatus.CONFLICT);

                                                }

                                            } else {

                                                resultado.put("codigo", ConstantesClaveUtilities.CODIGO_RESPUESTA_FALLIDO);
                                                resultado.put("descripcion", ConstantesClaveUtilities.DESCRIPCION_RESPUESTA_FALLIDO);
                                                respuesta.put("resultado", resultado);

                                                int bitacora_identificador = componente.registrar_bitacora_renapo(solicitud.toString(), new JSONObject(respuesta).toString(), 200);

                                                int solicitud_identificador = componente.registrar_solicitud_renapo(datos_portal.get("nombre") + "",
                                                        Integer.parseInt(datos_portal.get("identificador") + ""),
                                                        datos_cliente.get("clave_unica") + "",
                                                        bitacora_identificador);

                                                //Modificacion Excepciones
                                                componente.registrar_servicio_renapo("-1", "ETIQUETA RESPUESTARENAPO NO ENCONTRADA",
                                                        "-1", cuerpo, salida, solicitud_identificador);

                                                componente.actualizar_peticiones(1, 1);

                                                log.info("Respuesta: ");
                                                log.info(respuesta.toString());
                                                log.info("");
                                                log.info("");

                                                return new ResponseEntity(respuesta, HttpStatus.CONFLICT);

                                            }

                                        } else if ((json.get("codigoRespuesta") + "").trim().equals("01") || (json.get("codigoRespuesta") + "").trim().equals("05")) {

                                            resultado.put("codigo", ConstantesClaveUtilities.CODIGO_RESPUESTA_EXCEPCION);
                                            resultado.put("descripcion", ConstantesClaveUtilities.DESCRIPCION_RESPUESTA_EXCEPCION);
                                            respuesta.put("resultado", resultado);

                                            int bitacora_identificador = componente.registrar_bitacora_renapo(solicitud.toString(), new JSONObject(respuesta).toString(), 200);

                                            int solicitud_identificador = componente.registrar_solicitud_renapo(datos_portal.get("nombre") + "",
                                                    Integer.parseInt(datos_portal.get("identificador") + ""),
                                                    datos_cliente.get("clave_unica") + "",
                                                    bitacora_identificador);

                                            componente.registrar_servicio_renapo(json.get("codigoRespuesta").toString(), json.get("descripcionRespuesta").toString(),
                                                    json.get("referencia").toString(), cuerpo, salida, solicitud_identificador);

                                            componente.actualizar_peticiones(1, 1);

                                            log.info("Respuesta: ");
                                            log.info(respuesta.toString());
                                            log.info("");
                                            log.info("");

                                            return new ResponseEntity(respuesta, HttpStatus.CONFLICT);

                                        } else if ((json.get("codigoRespuesta") + "").trim().equals("02")) {

                                            if (json.get("descripcionRespuesta").toString().toLowerCase().contains("datos son incorrectos")) {

                                                resultado.put("codigo", json.get("codigoRespuesta").toString());
                                                resultado.put("descripcion", "No se puede verificar tu identidad, intentalo nuevamente.");
                                                respuesta.put("resultado", resultado);

                                                int bitacora_identificador = componente.registrar_bitacora_renapo(solicitud.toString(), new JSONObject(respuesta).toString(), 200);

                                                int solicitud_identificador = componente.registrar_solicitud_renapo(datos_portal.get("nombre") + "",
                                                        Integer.parseInt(datos_portal.get("identificador") + ""),
                                                        datos_cliente.get("clave_unica") + "",
                                                        bitacora_identificador);

                                                componente.registrar_servicio_renapo(json.get("codigoRespuesta").toString(), json.get("descripcionRespuesta").toString(),
                                                        json.get("referencia").toString(), cuerpo, salida, solicitud_identificador);

                                                componente.actualizar_peticiones(1, 1);

                                                log.info("Respuesta: ");
                                                log.info(respuesta.toString());
                                                log.info("");
                                                log.info("");

                                                return new ResponseEntity(respuesta, HttpStatus.CONFLICT);

                                            } else {

                                                resultado.put("codigo", json.get("codigoRespuesta").toString());
                                                resultado.put("descripcion", "Por favor intentar nuevamente, el día de mañana.");
                                                respuesta.put("resultado", resultado);

                                                int bitacora_identificador = componente.registrar_bitacora_renapo(solicitud.toString(), new JSONObject(respuesta).toString(), 200);

                                                int solicitud_identificador = componente.registrar_solicitud_renapo(datos_portal.get("nombre") + "",
                                                        Integer.parseInt(datos_portal.get("identificador") + ""),
                                                        datos_cliente.get("clave_unica") + "",
                                                        bitacora_identificador);

                                                componente.registrar_servicio_renapo(json.get("codigoRespuesta").toString(), json.get("descripcionRespuesta").toString(),
                                                        json.get("referencia").toString(), cuerpo, salida, solicitud_identificador);

                                                componente.actualizar_peticiones(1, 1);

                                                log.info("Respuesta: ");
                                                log.info(respuesta.toString());
                                                log.info("");
                                                log.info("");

                                                return new ResponseEntity(respuesta, HttpStatus.CONFLICT);

                                            }

                                        } else {

                                            resultado.put("codigo", json.get("codigoRespuesta").toString());
                                            resultado.put("descripcion", json.get("descripcionRespuesta").toString());
                                            respuesta.put("resultado", resultado);

                                            int bitacora_identificador = componente.registrar_bitacora_renapo(solicitud.toString(), new JSONObject(respuesta).toString(), 200);

                                            int solicitud_identificador = componente.registrar_solicitud_renapo(datos_portal.get("nombre") + "",
                                                    Integer.parseInt(datos_portal.get("identificador") + ""),
                                                    datos_cliente.get("clave_unica") + "",
                                                    bitacora_identificador);

                                            componente.registrar_servicio_renapo(json.get("codigoRespuesta").toString(), json.get("descripcionRespuesta").toString(),
                                                    json.get("referencia").toString(), cuerpo, salida, solicitud_identificador);

                                            componente.actualizar_peticiones(1, 1);

                                            log.info("Respuesta: ");
                                            log.info(respuesta.toString());
                                            log.info("");
                                            log.info("");

                                            return new ResponseEntity(respuesta, HttpStatus.CONFLICT);

                                        }

                                    } else {

                                        resultado.put("codigo", ConstantesClaveUtilities.CODIGO_RESPUESTA_FALLIDO);
                                        resultado.put("descripcion", ConstantesClaveUtilities.DESCRIPCION_RESPUESTA_FALLIDO);
                                        respuesta.put("resultado", resultado);

                                        int bitacora_identificador = componente.registrar_bitacora_renapo(solicitud.toString(), new JSONObject(respuesta).toString(), 200);

                                        int solicitud_identificador = componente.registrar_solicitud_renapo(datos_portal.get("nombre") + "",
                                                Integer.parseInt(datos_portal.get("identificador") + ""),
                                                datos_cliente.get("clave_unica") + "",
                                                bitacora_identificador);

                                        //Modificacion Excepciones
                                        componente.registrar_servicio_renapo("-1", "CAMPO CODIGORESPUESTA VACIO",
                                                "-1", cuerpo, salida, solicitud_identificador);

                                        componente.actualizar_peticiones(1, 1);

                                        log.info("Respuesta: ");
                                        log.info(respuesta.toString());
                                        log.info("");
                                        log.info("");

                                        return new ResponseEntity(respuesta, HttpStatus.CONFLICT);

                                    }

                                } else {

                                    resultado.put("codigo", ConstantesClaveUtilities.CODIGO_RESPUESTA_FALLIDO);
                                    resultado.put("descripcion", ConstantesClaveUtilities.DESCRIPCION_RESPUESTA_FALLIDO);
                                    respuesta.put("resultado", resultado);

                                    int bitacora_identificador = componente.registrar_bitacora_renapo(solicitud.toString(), new JSONObject(respuesta).toString(), 200);

                                    int solicitud_identificador = componente.registrar_solicitud_renapo(datos_portal.get("nombre") + "",
                                            Integer.parseInt(datos_portal.get("identificador") + ""),
                                            datos_cliente.get("clave_unica") + "",
                                            bitacora_identificador);

                                    //Modificacion Excepciones
                                    componente.registrar_servicio_renapo("-1", "ETIQUETA CODIGORESPUESTA NO ENCONTRADA",
                                            "-1", cuerpo, salida, solicitud_identificador);

                                    componente.actualizar_peticiones(1, 1);

                                    log.info("Respuesta: ");
                                    log.info(respuesta.toString());
                                    log.info("");
                                    log.info("");

                                    return new ResponseEntity(respuesta, HttpStatus.CONFLICT);

                                }

                            } else {

                                resultado.put("codigo", ConstantesClaveUtilities.CODIGO_RESPUESTA_FALLIDO);
                                resultado.put("descripcion", ConstantesClaveUtilities.DESCRIPCION_RESPUESTA_FALLIDO);
                                respuesta.put("resultado", resultado);

                                int bitacora_identificador = componente.registrar_bitacora_renapo(solicitud.toString(), new JSONObject(respuesta).toString(), 200);

                                int solicitud_identificador = componente.registrar_solicitud_renapo(datos_portal.get("nombre") + "",
                                        Integer.parseInt(datos_portal.get("identificador") + ""),
                                        datos_cliente.get("clave_unica") + "",
                                        bitacora_identificador);

                                //Modificacion Excepciones
                                componente.registrar_servicio_renapo("-1", "ETIQUETA RETURN NO ENCONTRADA",
                                        "-1", cuerpo, salida, solicitud_identificador);

                                componente.actualizar_peticiones(1, 1);

                                log.info("Respuesta: ");
                                log.info(respuesta.toString());
                                log.info("");
                                log.info("");

                                return new ResponseEntity(respuesta, HttpStatus.CONFLICT);

                            }

                        } else {

                            resultado.put("codigo", ConstantesClaveUtilities.CODIGO_RESPUESTA_FALLIDO);
                            resultado.put("descripcion", ConstantesClaveUtilities.DESCRIPCION_RESPUESTA_FALLIDO);
                            respuesta.put("resultado", resultado);

                            int bitacora_identificador = componente.registrar_bitacora_renapo(solicitud.toString(), new JSONObject(respuesta).toString(), 200);

                            int solicitud_identificador = componente.registrar_solicitud_renapo(datos_portal.get("nombre") + "",
                                    Integer.parseInt(datos_portal.get("identificador") + ""),
                                    datos_cliente.get("clave_unica") + "",
                                    bitacora_identificador);

                            //Modificacion Excepciones
                            componente.registrar_servicio_renapo("-1", "ETIQUETA VERIFICADATOSRESPONSE NO ENCONTRADA",
                                    "-1", cuerpo, salida, solicitud_identificador);

                            componente.actualizar_peticiones(1, 1);

                            log.info("Respuesta: ");
                            log.info(respuesta.toString());
                            log.info("");
                            log.info("");

                            return new ResponseEntity(respuesta, HttpStatus.CONFLICT);

                        }

                    } else {

                        resultado.put("codigo", ConstantesClaveUtilities.CODIGO_RESPUESTA_FALLIDO);
                        resultado.put("descripcion", ConstantesClaveUtilities.DESCRIPCION_RESPUESTA_FALLIDO);
                        respuesta.put("resultado", resultado);

                        int bitacora_identificador = componente.registrar_bitacora_renapo(solicitud.toString(), new JSONObject(respuesta).toString(), 200);

                        int solicitud_identificador = componente.registrar_solicitud_renapo(datos_portal.get("nombre") + "",
                                Integer.parseInt(datos_portal.get("identificador") + ""),
                                datos_cliente.get("clave_unica") + "",
                                bitacora_identificador);

                        //Modificacion Excepciones
                        componente.registrar_servicio_renapo("-1", "ETIQUETA BODY NO ENCONTRADA",
                                "-1", cuerpo, salida, solicitud_identificador);

                        componente.actualizar_peticiones(1, 1);

                        log.info("Respuesta: ");
                        log.info(respuesta.toString());
                        log.info("");
                        log.info("");

                        return new ResponseEntity(respuesta, HttpStatus.CONFLICT);

                    }

                } else {

                    resultado.put("codigo", ConstantesClaveUtilities.CODIGO_RESPUESTA_FALLIDO);
                    resultado.put("descripcion", ConstantesClaveUtilities.DESCRIPCION_RESPUESTA_FALLIDO);
                    respuesta.put("resultado", resultado);

                    int bitacora_identificador = componente.registrar_bitacora_renapo(solicitud.toString(), new JSONObject(respuesta).toString(), 200);

                    int solicitud_identificador = componente.registrar_solicitud_renapo(datos_portal.get("nombre") + "",
                            Integer.parseInt(datos_portal.get("identificador") + ""),
                            datos_cliente.get("clave_unica") + "",
                            bitacora_identificador);

                    //Modificacion Excepciones
                    componente.registrar_servicio_renapo("-1", "ETIQUETA ENVELOPE NO ENCONTRADA",
                            "-1", cuerpo, salida, solicitud_identificador);

                    componente.actualizar_peticiones(1, 1);

                    log.info("Respuesta: ");
                    log.info(respuesta.toString());
                    log.info("");
                    log.info("");

                    return new ResponseEntity(respuesta, HttpStatus.CONFLICT);

                }

            } else {

                resultado.put("codigo", ConstantesClaveUtilities.CODIGO_RESPUESTA_EXCEPCION);
                resultado.put("descripcion", ConstantesClaveUtilities.DESCRIPCION_RESPUESTA_EXCEPCION);
                respuesta.put("resultado", resultado);

                int bitacora_identificador = componente.registrar_bitacora_renapo(solicitud.toString(), new JSONObject(respuesta).toString(), 200);

                int solicitud_identificador = componente.registrar_solicitud_renapo(datos_portal.get("nombre") + "",
                        Integer.parseInt(datos_portal.get("identificador") + ""),
                        datos_cliente.get("clave_unica") + "",
                        bitacora_identificador);

                //Modificacion Excepciones
                componente.registrar_servicio_renapo("-1", "CODIGO DIFERENTE DE 200",
                        "-1", cuerpo, salida, solicitud_identificador);

                componente.actualizar_peticiones(1, 1);

                log.info("Respuesta: ");
                log.info(respuesta.toString());
                log.info("");
                log.info("");

                return new ResponseEntity(respuesta, HttpStatus.CONFLICT);

            }

        } catch (Exception e) {

            resultado.put("codigo", ConstantesClaveUtilities.CODIGO_RESPUESTA_EXCEPCION);
            resultado.put("descripcion", ConstantesClaveUtilities.DESCRIPCION_RESPUESTA_EXCEPCION);
            respuesta.put("resultado", resultado);

            int bitacora_identificador = componente.registrar_bitacora_renapo(solicitud.toString(), new JSONObject(respuesta).toString(), 200);

            int solicitud_identificador = componente.registrar_solicitud_renapo(datos_portal.get("nombre") + "",
                    Integer.parseInt(datos_portal.get("identificador") + ""),
                    datos_cliente.get("clave_unica") + "",
                    bitacora_identificador);

            //Modificacion Excepciones
            componente.registrar_servicio_renapo("-2", "EXCEPCION",
                    "-2", cuerpo, e.getMessage(), solicitud_identificador);

            componente.actualizar_peticiones(1, 1);

            log.info("Exepcion: ");
            log.info(e.getMessage());
            log.info("");
            log.info("");

            log.info("Respuesta: ");
            log.info(respuesta.toString());
            log.info("");
            log.info("");

            return new ResponseEntity(respuesta, HttpStatus.CONFLICT);

        }

    }

    private String solicitud_validar(JSONObject solicitud) {

        JSONObject informacion = new JSONObject(solicitud.get("datos_cliente").toString());

        JSONObject datos_cliente = new JSONObject();
        datos_cliente.put("curp", (informacion.get("clave_unica") + "").trim());

        // Modificacion para obtener parametro
        String parametro = componente.obtener_parametro(PropiedadesUtilities.PARAMETRO_CONFIGURACION_SERVICIO_CURP);

        log.info("Parametro: ");
        log.info(parametro);
        log.info("");
        log.info("");

        if (parametro.equals("true")) {

            log.info("Modificar CURP.");
            log.info("");
            log.info("");

            String curp = componente.obtener_parametro(PropiedadesUtilities.PARAMETRO_DINAMICO_CURP);

            datos_cliente.put("curp", curp);

        }

        JSONObject datos_institucion = new JSONObject();
        LocalDateTime tiempo = LocalDateTime.now();
        datos_institucion.put("idInstitucion", PropiedadesUtilities.INSTITUCION_CLAVE);
        datos_institucion.put("idEstacion", PropiedadesUtilities.ESTACION_CLAVE);
        datos_institucion.put("fechaHora", PropiedadesUtilities.FECHA_CLAVE);
        datos_institucion.put("referencia", "F-" + tiempo.getHour() + tiempo.getMinute() + tiempo.getSecond() + tiempo.getNano());

        JSONObject componente_cliente = new JSONObject();
        componente_cliente.put("nombreAplicativo", PropiedadesUtilities.APLICATIVO_CLAVE);
        componente_cliente.put("versionAplicativo", PropiedadesUtilities.VERSION_CLAVE);

        JSONObject cuerpo = new JSONObject();
        cuerpo.put("versionJSON", PropiedadesUtilities.JSON_CLAVE);
        cuerpo.put("tipoValidacion", PropiedadesUtilities.VALIDACION_CLAVE);
        cuerpo.put("tipoFlujo", PropiedadesUtilities.FLUJO_CLAVE);
        cuerpo.put("datosCliente", datos_cliente);
        cuerpo.put("datosInstitucion", datos_institucion);
        cuerpo.put("componenteCliente", componente_cliente);

        return cuerpo.toString();

    }

}
