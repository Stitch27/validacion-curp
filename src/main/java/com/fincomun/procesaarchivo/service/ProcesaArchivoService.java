package com.fincomun.procesaarchivo.service;

import com.fincomun.procesaarchivo.model.*;
import com.fincomun.validadorcurp.component.ConsultasClaveComponent;
import com.fincomun.validadorcurp.utilities.PropiedadesUtilities;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProcesaArchivoService {

    @Autowired
    private ConsultasClaveComponent consultasClaveComponent;

    @Async
    public void procesaContenidoArchivo(String nombreArchivo) {
        log.info("");
        log.info("=============== ProcesaArchivoService, Metodo: procesaContenidoArchivo ===============");
        log.info("");
        List<ContenidoArchivo> contenidoArchivoList = new ArrayList<>();
        String nombreFinal = PropiedadesUtilities.RUTA_ARCHIVO_PROCESAR + nombreArchivo;
        File archivo = new File(nombreFinal);
        String respuesta;
        if (archivo.exists()) {
            log.info("El archivo existe");
            try {
                BufferedReader lector = new BufferedReader(new FileReader(nombreFinal));
                String fila;
                boolean primeraFila = true;
                while ((fila = lector.readLine()) != null) {
                    // Ignorar filas vacías
                    if (fila.trim().isEmpty()) {
                        continue;
                    }

                    if (primeraFila) {
                        primeraFila = false;
                        continue;
                    }
                    ContenidoArchivo registro = getContenidoArchivo(fila);

                    contenidoArchivoList.add(registro);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                log.error("-3, error al leer el archivo");
            }
            guardaBitacora(contenidoArchivoList);
        } else {
            log.error("-2, el archivo no existe");
        }
        log.info("");
        log.info("=============== finaliza: procesaContenidoArchivo ===============");
    }

    private void guardaBitacora(List<ContenidoArchivo> contenidoArchivoList) {
        log.info("");
        log.info("=============== ProcesaArchivoService, Metodo: guardaBitacora ===============");
        log.info("");
        if (contenidoArchivoList == null || contenidoArchivoList.isEmpty()) {
            log.error("-1, archivo en blanco");
        } else {

            int errorCount = 0;
            int successCount = 0;
            int totalCount = 0;
            int curpVacio = 0;
            for (ContenidoArchivo fila: contenidoArchivoList) {
                JsonRespuesta respuesta = tranformaJson(fila.getJsonRespuesta());
                if (respuesta == null) {
                    log.error("-4, error al transformar json");
                    continue;
                }
                totalCount++;
                int estado;
                if (fila.getMessage().contains("LA OPERACION SE EJECUTO")) {
                    successCount++;
                    estado = 300;
                } else {
                    errorCount++;
                    estado = 200;
                }

                PeticionRenapoJson peticionRenapoJson = new PeticionRenapoJson();
                peticionRenapoJson.setDatosCliente(new DatosClienteRenapo(fila.getCurp()));
                peticionRenapoJson.setDatosPortal(new DatosPortal());

                log.info("Registrando en bitacora");

                Integer idBitacora = consultasClaveComponent
                        .registrar_bitacora_renapo(peticionRenapoJson.toString(), fila.getJsonRespuesta(), estado);

                log.info("idBitacora: " + idBitacora);

                if (fila.getCurp() == null || fila.getCurp().isEmpty()) {
                    log.error("curp vacio");
                    curpVacio++;
                    continue;
                }

                Integer idPeticion = consultasClaveComponent
                        .registrar_solicitud_renapo("Masivos", 106, fila.getCurp(), idBitacora);

                log.info("idPeticion: " + idPeticion);

                Integer idServicio = consultasClaveComponent
                        .registrar_servicio_renapo(respuesta.getCodigoRespuesta(), respuesta.getDescripcionRespuesta(), respuesta.getReferencia(), " ", " ", idPeticion);

                log.info("idServicio: " + idServicio);
            }
            int erroresReales = errorCount - curpVacio;
            log.info("Numero de registros: " + totalCount);
            log.info("Total de errores: " + erroresReales);
            log.info("Total de exitos: " + successCount);
            log.info("Total de curp vacios: " + curpVacio);
        }
    }

    private JsonRespuesta tranformaJson(String json) {
        Gson gson = new Gson();
        try {
            // Convertir JSON a objeto
            return gson.fromJson(json, JsonRespuesta.class);
        } catch (Exception e) {
            log.error("Error al convertir JSON a objeto: " + e.getMessage());
            return null;
        }
    }

    private ContenidoArchivo getContenidoArchivo(String fila) {
        String[] contenido = fila.split("\\|");
        ContenidoArchivo registro = new ContenidoArchivo();

        if (contenido.length == 7) {
            registro.setCurp(contenido[0]);
            registro.setMessage(contenido[1]);
            registro.setNombre(contenido[2]);
            registro.setApellidoPaterno(contenido[3]);
            registro.setApellidoMaterno(contenido[4]);
            registro.setFechaNacimiento(contenido[5]);
            registro.setJsonRespuesta(contenido[6]);
        } else if (contenido.length == 9) {
            registro.setCurp(contenido[0]);
            registro.setMessage(contenido[1]);
            registro.setNombre(contenido[2]);
            registro.setApellidoPaterno(contenido[3]);
            registro.setApellidoMaterno(contenido[4]);
            registro.setFechaNacimiento(contenido[5]);
            registro.setEntidad(contenido[6]);
            registro.setSexo(contenido[7]);
            registro.setJsonRespuesta(contenido[8]);
        } else {
            log.error("Existe mas informacion de lo esperado");
        }

        return registro;
    }
}
