package com.fincomun.procesaarchivo.service;

import com.fincomun.procesaarchivo.dto.RegistraArchivoDto;
import com.fincomun.procesaarchivo.model.*;
import com.fincomun.procesaarchivo.repository.GuardaBitacoraRepository;
import com.fincomun.procesaarchivo.repository.GuardaServicioRepository;
import com.fincomun.procesaarchivo.repository.GuardaSolicitudRepository;
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
    private GuardaBitacoraRepository guardaBitacoraRepository;

    @Autowired
    private GuardaSolicitudRepository guardaSolicitudRepository;

    @Autowired
    private GuardaServicioRepository guardaServicioRepository;

    @Autowired
    private GuardaRegistroCurp guardaRegistroCurp;

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
            guardaRegistroCurp(contenidoArchivoList);
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
            for (ContenidoArchivo fila : contenidoArchivoList) {
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

                // Integer idBitacora = consultasClaveComponent
                // .registrar_bitacora_renapo(peticionRenapoJson.toString(),
                // fila.getJsonRespuesta(), estado);
                Integer idBitacora = guardaBitacoraRepository.guardarBitacora(peticionRenapoJson.toString(),
                        fila.getJsonRespuesta(), estado);

                log.info("idBitacora: " + idBitacora);

                if (idBitacora == -1) {
                    log.error("error al guardar en bitacora");
                    continue;
                }

                if (fila.getCurp() == null || fila.getCurp().isEmpty()) {
                    log.error("curp vacio");
                    curpVacio++;
                    continue;
                }

                // Integer idPeticion = consultasClaveComponent
                // .registrar_solicitud_renapo("Masivos", 106, fila.getCurp(), idBitacora);
                Integer idPeticion = guardaSolicitudRepository.guardarSolicitud("Masivos", 106, fila.getCurp(),
                        idBitacora);

                log.info("idPeticion: " + idPeticion);

                if (idPeticion == -1) {
                    log.error("error al guardar en solicitud");
                    continue;
                }

                // Integer idServicio = consultasClaveComponent
                // .registrar_servicio_renapo(respuesta.getCodigoRespuesta(),
                // respuesta.getDescripcionRespuesta(), respuesta.getReferencia(), " ", " ",
                // idPeticion);
                Integer idServicio = guardaServicioRepository
                        .guardarServicio(respuesta.getCodigoRespuesta(), respuesta.getDescripcionRespuesta(),
                                respuesta.getReferencia(), " ", " ", idPeticion);

                log.info("idServicio: " + idServicio);

                if (idServicio == -1) {
                    log.error("error al guardar en servicio");
                    continue;
                }
            }
            int erroresReales = errorCount - curpVacio;
            log.info("Numero de registros: " + totalCount);
            log.info("Total de errores: " + erroresReales);
            log.info("Total de exitos: " + successCount);
            log.info("Total de curp vacios: " + curpVacio);
        }
    }

    private void guardaRegistroCurp(List<ContenidoArchivo> contenidoArchivoList) {
        log.info("=============== ProcesaArchivoService, Metodo: guardaRegistroCurp ===============");

        if (contenidoArchivoList == null || contenidoArchivoList.isEmpty()) {
            log.error("-1, archivo en blanco");
        } else {
            int errorCount = 0;
            int successCount = 0;
            int totalCount = 0;
            int curpVacio = 0;
            int dataBaseError = 0;
            for (ContenidoArchivo fila : contenidoArchivoList) {
                totalCount++;

                // 1. Validaciones previas
                if (fila.getCurp() == null || fila.getCurp().isEmpty()) {
                    log.error("Fila {}: curp vacio", totalCount);
                    curpVacio++;
                    continue;
                }

                JsonRespuesta respuesta = tranformaJson(fila.getJsonRespuesta());
                if (respuesta == null) {
                    log.error("Fila {}: error al transformar json", totalCount);
                    errorCount++;
                    continue;
                }

                // 2. Preparación de datos
                int estado = fila.getMessage().contains("LA OPERACION SE EJECUTO") ? 300 : 200;
                if (estado == 300) {
                    successCount++;
                } else {
                    errorCount++;
                }

                // seteo del DTO
                PeticionRenapoJson peticionRenapoJson = new PeticionRenapoJson();
                peticionRenapoJson.setDatosCliente(new DatosClienteRenapo(fila.getCurp()));
                peticionRenapoJson.setDatosPortal(new DatosPortal());

                RegistraArchivoDto rArchivoDto = new RegistraArchivoDto();

                rArchivoDto.setBitSolicitud(peticionRenapoJson.toString());
                rArchivoDto.setBitRespuesta(fila.getJsonRespuesta());
                rArchivoDto.setEstado(estado);

                rArchivoDto.setSolClienteCurp(fila.getCurp());

                rArchivoDto.setSerCodigo(respuesta.getCodigoRespuesta());
                rArchivoDto.setSerDescripcion(respuesta.getDescripcionRespuesta());
                rArchivoDto.setSerRespuesta(respuesta.getReferencia());

                // 3. Intento de persistencia (Transacción independiente)
                try {
                    guardaRegistroCurp.guardaRegistroCurp(rArchivoDto);
                } catch (Exception e) {
                    log.error("Falla persistencia fila {}: {}", totalCount, e.getMessage());
                    dataBaseError++;
                }
            }
            log.info("Numero de registros: " + totalCount);
            log.info("Exitos (Negocio): " + successCount);
            log.info("Errores (Negocio): " + errorCount);
            log.info("CURPs Vacíos (No procesados): " + curpVacio);
            log.info("Errores de persistencia (BD): " + dataBaseError);
        }

        log.info("=============== finaliza: guardaRegistroCurp ===============");
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
