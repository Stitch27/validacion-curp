package com.fincomun.identificacion.component;

import java.util.Base64;
import org.json.JSONObject;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.HttpClient;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import org.apache.http.entity.StringEntity;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.http.client.methods.HttpPost;
import org.springframework.stereotype.Component;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import com.fincomun.identificacion.model.RespuestaValidarIneModel;
import com.fincomun.identificacion.utilities.PropiedadesFimpeINEUtilities;

@Slf4j
@Component
public class ServiciosComponent {

    public RespuestaValidarIneModel servicio_validar_ine(String cic, String id_ciudadano) {

        RequestConfig configuracion = RequestConfig.custom()
                .setConnectTimeout(Integer.parseInt(PropiedadesFimpeINEUtilities.VALOR_TIMEPO_ESPERA_SERVICIOS))
                .setConnectionRequestTimeout(Integer.parseInt(PropiedadesFimpeINEUtilities.VALOR_TIMEPO_ESPERA_SERVICIOS))
                .setSocketTimeout(Integer.parseInt(PropiedadesFimpeINEUtilities.VALOR_TIMEPO_ESPERA_SERVICIOS)).build();

        HttpClient cliente = HttpClientBuilder.create().setDefaultRequestConfig(configuracion).build();

        HttpPost operacion = new HttpPost(PropiedadesFimpeINEUtilities.VALOR_DIRECCION_SERVICIO_VALIDACION_INE);
        operacion.addHeader("Content-Type", PropiedadesFimpeINEUtilities.VALOR_CABECERA_SERVICIO_LISTA_NOMINALES);
        operacion.addHeader("Authorization", authorizacion());

        String referencia = generar_referencia();
        String fecha = generar_fecha();

        JSONObject peticion = new JSONObject();
        peticion.put("cic", cic);
        peticion.put("identificadorCiudadano", id_ciudadano);
        peticion.put("referencia", referencia);
        peticion.put("institucion", PropiedadesFimpeINEUtilities.VALOR_INSTITUCION_SERVICIO_VALIDACION_INE);
        peticion.put("fecha", fecha);

        log.info("PARAMETROS DEL SERVICIO DE VALIDACION INE");
        log.info(peticion.toString());
        log.info("");
        log.info("");

        try {

            StringEntity entidad = new StringEntity(peticion.toString(), StandardCharsets.UTF_8);
            entidad.setContentType(PropiedadesFimpeINEUtilities.VALOR_CABECERA_SERVICIO_LISTA_NOMINALES + "; "
                    + PropiedadesFimpeINEUtilities.VALOR_CODIFICACION_SERVICIOS);

            operacion.setEntity(entidad);

            HttpResponse respuesta = cliente.execute(operacion);

            String salida = EntityUtils.toString(respuesta.getEntity(), StandardCharsets.UTF_8);

            JSONObject resultado = new JSONObject(salida);

            log.info("RESPUESTA DEL SERVICIO VALIDACION INE");
            log.info(resultado.toString());
            log.info("");
            log.info("");

            if (respuesta.getStatusLine().getStatusCode() == 200) {

                if (resultado.has("estatus")) {

                    RespuestaValidarIneModel validar = new RespuestaValidarIneModel();

                    if (resultado.get("estatus").toString().equals("OK")) {

                        log.info("ESTATUS OK");
                        log.info("");
                        log.info("");

                        validar.setEstatus(resultado.optString("estatus", ""));
                        validar.setMensaje(resultado.optString("mensaje", ""));
                        validar.setCodigoValidacion(resultado.optString("codigoValidacion", ""));
                        validar.setClaveMensaje(resultado.optString("claveMensaje", ""));
                        validar.setClaveElector(resultado.optString("claveElector", ""));
                        validar.setNumeroEmision(resultado.optString("numeroEmision", ""));
                        validar.setAnioRegistro(resultado.optString("anioRegistro", ""));
                        validar.setAnioEmision(resultado.optString("anioEmision", ""));
                        validar.setVigencia(resultado.optString("vigencia", ""));
                        validar.setOcr(resultado.optString("ocr", ""));
                        validar.setCic(resultado.optString("cic", ""));
                        validar.setDistritoFederal(resultado.optString("distritoFederal", ""));
                        validar.setInformacionAdicional(resultado.optString("informacionAdicional", ""));
                        validar.setReferencia(referencia);
                        validar.setFecha(fecha);

                        return validar;

                    } else {

                        log.info("ESTATUS ERROR");
                        log.info("");
                        log.info("");

                        validar.setEstatus(resultado.optString("estatus", ""));
                        validar.setMensaje(resultado.optString("mensaje", ""));
                        validar.setCodigoValidacion(resultado.optString("codigoValidacion", ""));
                        validar.setClaveMensaje(resultado.optString("claveMensaje", ""));
                        validar.setClaveElector(resultado.optString("claveElector", ""));
                        validar.setNumeroEmision(resultado.optString("numeroEmision", ""));
                        validar.setAnioRegistro(resultado.optString("anioRegistro", ""));
                        validar.setAnioEmision(resultado.optString("anioEmision", ""));
                        validar.setVigencia(resultado.optString("vigencia", ""));
                        validar.setOcr(resultado.optString("ocr", ""));
                        validar.setCic(resultado.optString("cic", ""));
                        validar.setDistritoFederal(resultado.optString("distritoFederal", ""));
                        validar.setInformacionAdicional(resultado.optString("informacionAdicional", ""));
                        validar.setReferencia(referencia);
                        validar.setFecha(fecha);

                        return validar;

                    }

                } else {

                    log.info("RESPUESTA MAL FORMADA");
                    log.info("");
                    log.info("");

                    return null;

                }

            } else {

                log.info("CODIGO DE RESPUESTA");
                log.info(respuesta.getStatusLine().getStatusCode() + "");
                log.info("");
                log.info("");

                return null;

            }

        } catch (Exception e) {

            log.error("EXCEPCION");
            log.error(e.getMessage());
            log.error("");
            log.error("");

            return null;

        }

    }

    private static String generar_referencia() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyyHHmmssSSS");
        int random = ThreadLocalRandom.current().nextInt(100, 999);

        return "FC" + LocalDateTime.now().format(formatter) + random;

    }

    public static String generar_fecha() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
        return LocalDateTime.now().format(formatter);

    }

    private String authorizacion() {

        String credenciales = PropiedadesFimpeINEUtilities.VALOR_USUARIO_SERVICIO_VALIDACION_INE + ":"
                + PropiedadesFimpeINEUtilities.VALOR_CONTRASENA_SERVICIO_VALIDACION_INE;

        return "Basic " + Base64.getEncoder().encodeToString(credenciales.getBytes(StandardCharsets.UTF_8));

    }

}
