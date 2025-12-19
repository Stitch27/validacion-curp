package com.fincomun.validadorcurp.utilities;

import java.util.Objects;
import java.util.HashMap;
import java.util.LinkedHashMap;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.fincomun.validadorcurp.model.ValidarPeticionesModel;

@Component
public class ValidarPeticionesUtilities {

    private static final Logger LOG = Logger.getLogger(ValidarPeticionesUtilities.class);

    public Integer tipo(ValidarPeticionesModel solicitud) {

        if (Objects.nonNull(solicitud.getTipo())) {

            if (!solicitud.getTipo().trim().isEmpty()) {

                if (StringUtils.isNumeric(solicitud.getTipo().trim())) {

                    return 0;

                } else {

                    return 3;

                }

            } else {

                return 2;

            }

        } else {

            return 1;

        }

    }

    public Integer peticiones(ValidarPeticionesModel solicitud) {

        if (Objects.nonNull(solicitud.getNumero_peticiones())) {

            if (!solicitud.getNumero_peticiones().trim().isEmpty()) {

                if (StringUtils.isNumeric(solicitud.getNumero_peticiones().trim())) {

                    return 0;

                } else {

                    return 3;

                }

            } else {

                return 2;

            }

        } else {

            return 1;

        }

    }

    public Integer configuracion(ValidarPeticionesModel solicitud) {

        if (Objects.nonNull(solicitud.getConfiguracion())) {

            if (Objects.nonNull(solicitud.getConfiguracion().getInstitucion())) {

                if (!solicitud.getConfiguracion().getInstitucion().trim().isEmpty()) {

                    if (Objects.nonNull(solicitud.getConfiguracion().getIdentificador())) {

                        if (!solicitud.getConfiguracion().getIdentificador().trim().isEmpty()) {

                            if (StringUtils.isNumeric(solicitud.getConfiguracion().getIdentificador().trim())) {

                                return 0;

                            } else {

                                return 6;

                            }

                        } else {

                            return 5;

                        }

                    } else {

                        return 4;

                    }

                } else {

                    return 3;

                }

            } else {

                return 2;

            }

        } else {

            return 1;

        }

    }

    public Integer proceso(ValidarPeticionesModel solicitud) {

        Integer tipo = Integer.parseInt(solicitud.getTipo().trim());

        switch (tipo) {
            case 1: {

                Integer peticiones = Integer.parseInt(solicitud.getNumero_peticiones().trim());

                if (peticiones == 1) {

                    return 100;

                } else {

                    return 2;

                }

            }
            case 2: {

                Integer peticiones = Integer.parseInt(solicitud.getNumero_peticiones().trim());

                if (peticiones > 1) {

                    return 200;

                } else {

                    return 2;

                }

            }
            default:
                return 1;
        }

    }

    public ResponseEntity<Object> individual(Integer peticiones_permitidas, Integer peticiones_realizadas) {

        HashMap<String, String> resultado = new LinkedHashMap<>();
        HashMap<String, Object> respuesta = new LinkedHashMap<>();

        LOG.info("=== Individual");

        if ((peticiones_realizadas + 1) <= peticiones_permitidas) {

            HashMap<String, Object> informacion = new LinkedHashMap<>();
            informacion.put("peticiones_restantes", peticiones_permitidas - (peticiones_realizadas + 1));

            resultado.put("codigo", ConstantesValidarPeticionesUtilities.CODIGO_EXITO);
            resultado.put("descripcion", ConstantesValidarPeticionesUtilities.DESCRIPCION_EXITO);

            respuesta.put("resultado", resultado);
            respuesta.put("informacion", informacion);
            return new ResponseEntity(respuesta, HttpStatus.OK);

        } else {

            resultado.put("codigo", ConstantesValidarPeticionesUtilities.CODIGO_PETICIONES);
            resultado.put("descripcion", ConstantesValidarPeticionesUtilities.DESCRIPCION_PETICIONES);

            respuesta.put("resultado", resultado);
            return new ResponseEntity(respuesta, HttpStatus.CONFLICT);

        }

    }

    public ResponseEntity<Object> masivo(Integer peticiones_permitidas, Integer peticiones_realizadas, Integer peticiones) {

        HashMap<String, String> resultado = new LinkedHashMap<>();
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        HashMap<String, Object> informacion = new LinkedHashMap<>();

        if ((peticiones_realizadas + peticiones) < peticiones_permitidas) {

            informacion.put("peticiones_restantes", (peticiones_permitidas - (peticiones_realizadas + peticiones)));

            resultado.put("codigo", ConstantesValidarPeticionesUtilities.CODIGO_EXITO);
            resultado.put("descripcion", ConstantesValidarPeticionesUtilities.DESCRIPCION_EXITO);

            respuesta.put("resultado", resultado);
            respuesta.put("informacion", informacion);
            return new ResponseEntity(respuesta, HttpStatus.OK);

        } else {

            informacion.put("solicitides_aprobadas", peticiones_permitidas - peticiones_realizadas);
            informacion.put("solicitudes_pendientes", (peticiones_realizadas + peticiones) - peticiones_permitidas);

            resultado.put("codigo", ConstantesValidarPeticionesUtilities.CODIGO_PENDIENTES);
            resultado.put("descripcion", ConstantesValidarPeticionesUtilities.DESCRIPCION_PENDIENTES);

            respuesta.put("resultado", resultado);
            respuesta.put("informacion", informacion);
            return new ResponseEntity(respuesta, HttpStatus.CONFLICT);

        }

    }

}
