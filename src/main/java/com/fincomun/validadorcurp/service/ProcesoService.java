package com.fincomun.validadorcurp.service;

import java.io.File;
import java.util.List;
import java.io.FileReader;
import java.util.ArrayList;
import org.json.JSONObject;
import java.io.BufferedReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.StringEntity;
import org.springframework.stereotype.Service;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.scheduling.annotation.Async;
import com.fincomun.validadorcurp.model.ProcesoClaveModel;
import com.fincomun.validadorcurp.utilities.PropiedadesUtilities;
import com.fincomun.validadorcurp.model.ProcesoIdentificacionModel;

@Slf4j
@Service
public class ProcesoService {

    @Async
    public void secuencia(Integer tipo) {

        log.info("");
        log.info("");
        log.info(".......... Inicio Proceso ..........");
        log.info("");

        if (tipo == 0) {

            List<ProcesoClaveModel> registros = archivo_claves();

            if (registros != null) {

                if (registros.size() != 0) {

                    for (ProcesoClaveModel valor : registros) {

                        JSONObject datos_cliente = new JSONObject();
                        datos_cliente.put("clave_unica", valor.getClave());

                        JSONObject datos_portal = new JSONObject();
                        datos_portal.put("identificador", valor.getIdentificador());
                        datos_portal.put("nombre", valor.getNombre());

                        JSONObject peticion = new JSONObject();
                        peticion.put("datos_portal", datos_portal);
                        peticion.put("datos_cliente", datos_cliente);
                        peticion.put("proceso", "10");

                        HttpClient cliente = HttpClientBuilder.create().build();

                        HttpPost operacion = new HttpPost(PropiedadesUtilities.DIRECCION_CLAVES);
                        operacion.addHeader("content-Type", "application/json");
                        operacion.addHeader("Accept", "application/json");

                        try {

                            StringEntity entidad = new StringEntity(peticion.toString());
                            operacion.setEntity(entidad);

                            HttpResponse resultado = cliente.execute(operacion);

                            if (resultado.getStatusLine().getStatusCode() == 200) {

                                String respuesta = EntityUtils.toString(resultado.getEntity());

                                JSONObject json = new JSONObject(respuesta);
                                JSONObject salida = new JSONObject(json.get("resultado").toString());

                                log.info("..... Resultados .....");
                                log.info(resultado.getStatusLine().getStatusCode() + ", " + valor.getClave() + ", " + salida.get("codigo") + ", " + salida.get("descripcion"));
                                log.info("");
                                log.info("");

                            } else {

                                String respuesta = EntityUtils.toString(resultado.getEntity());

                                JSONObject json = new JSONObject(respuesta);
                                JSONObject salida = new JSONObject(json.get("resultado").toString());

                                log.info("..... Resultados .....");
                                log.info(resultado.getStatusLine().getStatusCode() + ", " + valor.getClave() + ", " + salida.get("codigo") + ", " + salida.get("descripcion"));
                                log.info("");
                                log.info("");
                            }

                        } catch (Exception e) {

                            log.info("..... No fue posible procesar la petición .....");
                            log.info("");
                            log.info("");

                        }

                    }

                } else {

                    log.info("..... Sin registros que procesar .....");
                    log.info("");
                    log.info("");

                }

            } else {

                log.info("..... No fue posible leer el archivo .....");
                log.info("");
                log.info("");

            }

        } else {

            List<ProcesoIdentificacionModel> registros = archivo_identificaciones();

            if (registros != null) {

                if (registros.size() != 0) {

                    int contador = 1;

                    for (ProcesoIdentificacionModel valor : registros) {

                        JSONObject datos_cliente = new JSONObject();
                        datos_cliente.put("nombre", valor.getNombre());
                        datos_cliente.put("apellido_paterno", valor.getApellido_paterno());
                        datos_cliente.put("apellido_materno", valor.getApellido_materno());
                        datos_cliente.put("reconocimiento_optico", valor.getReconocimiento_optico());
                        datos_cliente.put("a_registro", valor.getA_registro());
                        datos_cliente.put("a_emision", valor.getA_emision());
                        datos_cliente.put("clave_unica", valor.getClave_unica());
                        datos_cliente.put("n_emision", valor.getN_emision());
                        datos_cliente.put("clave_elector", valor.getClave_elector());
                        datos_cliente.put("codigo_identificacion", valor.getCodigo_identificacion());

                        JSONObject datos_portal = new JSONObject();
                        datos_portal.put("identificador", valor.getIdentificador_portal());
                        datos_portal.put("nombre", valor.getNombre_portal());

                        JSONObject peticion = new JSONObject();
                        peticion.put("datos_portal", datos_portal);
                        peticion.put("datos_cliente", datos_cliente);

                        HttpClient cliente = HttpClientBuilder.create().build();

                        HttpPost operacion = new HttpPost(PropiedadesUtilities.DIRECCION_IDENTIFICACIONES);
                        operacion.addHeader("content-Type", "application/json");
                        operacion.addHeader("Accept", "application/json");

                        try {

                            StringEntity entidad = new StringEntity(peticion.toString());
                            operacion.setEntity(entidad);

                            HttpResponse resultado = cliente.execute(operacion);

                            if (resultado.getStatusLine().getStatusCode() == 200) {

                                String respuesta = EntityUtils.toString(resultado.getEntity());

                                JSONObject json = new JSONObject(respuesta);
                                JSONObject salida = new JSONObject(json.get("resultado").toString());

                                log.info("..... Resultados .....");
                                log.info(resultado.getStatusLine().getStatusCode() + ", " + valor.getClave_unica() + ", " + salida.get("codigo") + ", " + salida.get("descripcion"));
                                log.info("");
                                log.info("");

                            } else {

                                String respuesta = EntityUtils.toString(resultado.getEntity());

                                JSONObject json = new JSONObject(respuesta);
                                JSONObject salida = new JSONObject(json.get("resultado").toString());

                                log.info("..... Resultados .....");
                                log.info(resultado.getStatusLine().getStatusCode() + ", " + valor.getClave_unica() + ", " + salida.get("codigo") + ", " + salida.get("descripcion"));
                                log.info("");
                                log.info("");

                            }

                            if (contador == 10) {

                                Thread.sleep(60000);
                                contador = 1;

                            } else {

                                contador++;

                            }

                        } catch (Exception e) {

                            log.info("..... No fue posible procesar la petición .....");
                            log.info("");
                            log.info("");

                        }

                    }

                } else {

                    log.info("..... Sin registros que procesar .....");
                    log.info("");
                    log.info("");

                }

            } else {

                log.info("..... No fue posible leer el archivo .....");
                log.info("");
                log.info("");

            }

        }

        log.info(".......... Fin Proceso ..........");

    }

    private List<ProcesoClaveModel> archivo_claves() {

        List<ProcesoClaveModel> claves = new ArrayList<>();

        FileReader leer_archivo;

        try {

            File archivo = new File(PropiedadesUtilities.NOMBRE_CLAVES);

            leer_archivo = new FileReader(archivo);

            BufferedReader leer = new BufferedReader(leer_archivo);

            ProcesoClaveModel modelo;

            String linea;

            while ((linea = leer.readLine()) != null) {

                String[] datos = linea.split(",");

                modelo = new ProcesoClaveModel();

                modelo.setIdentificador(datos[0]);
                modelo.setNombre(datos[1]);
                modelo.setClave(datos[2]);

                claves.add(modelo);

            }

            leer_archivo.close();

        } catch (Exception e) {

            return null;

        }

        return claves;

    }

    private List<ProcesoIdentificacionModel> archivo_identificaciones() {

        List<ProcesoIdentificacionModel> identificaciones = new ArrayList<>();

        FileReader leer_archivo;

        try {

            File archivo = new File(PropiedadesUtilities.NOMBRE_IDENTIFICACIONES);

            leer_archivo = new FileReader(archivo);

            BufferedReader leer = new BufferedReader(leer_archivo);

            ProcesoIdentificacionModel modelo;

            String linea;

            while ((linea = leer.readLine()) != null) {

                String[] datos = linea.split(",");

                modelo = new ProcesoIdentificacionModel();

                modelo.setIdentificador_portal(datos[0]);
                modelo.setNombre_portal(datos[1]);
                modelo.setNombre(datos[2]);
                modelo.setApellido_paterno(datos[3]);
                modelo.setApellido_materno(datos[4]);
                modelo.setReconocimiento_optico(datos[5]);
                modelo.setA_registro(datos[6]);
                modelo.setA_emision(datos[7]);
                modelo.setClave_unica(datos[8]);
                modelo.setN_emision(datos[9]);
                modelo.setClave_elector(datos[10]);
                modelo.setCodigo_identificacion(datos[11]);

                identificaciones.add(modelo);

            }

            leer_archivo.close();

        } catch (Exception e) {

            return null;

        }

        return identificaciones;

    }

}
