package com.fincomun.validadorcurp.utilities;

import org.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class IdentificacionUtilities {

    public Integer validar_portal(JSONObject solicitud) {

        if (solicitud.has("datos_portal")) {

            JSONObject portal = new JSONObject(solicitud.get("datos_portal").toString());

            if (portal.has("identificador")) {

                if (!(portal.get("identificador") + "").trim().isEmpty()) {

                    if (StringUtils.isNumeric((portal.get("identificador") + "").trim())) {

                        if (portal.has("nombre")) {

                            if (!(portal.get("nombre") + "").trim().isEmpty()) {

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

    public Integer validar_cliente(JSONObject solicitud) {

        if (solicitud.has("datos_cliente")) {

            JSONObject cliente = new JSONObject(solicitud.get("datos_cliente").toString());

            if (cliente.has("nombre")) {

                if (!(cliente.get("nombre") + "").trim().isEmpty()) {

                    if (cliente.has("apellido_paterno")) {

                        if (!(cliente.get("apellido_paterno") + "").trim().isEmpty()) {

                            if (cliente.has("apellido_materno")) {

                                if (!(cliente.get("apellido_materno") + "").trim().isEmpty()) {

                                    if (cliente.has("reconocimiento_optico")) {

                                        if (!(cliente.get("reconocimiento_optico") + "").trim().isEmpty()) {

                                            if (cliente.has("a_registro")) {

                                                if (!(cliente.get("a_registro") + "").trim().isEmpty()) {

                                                    if (cliente.has("a_emision")) {

                                                        if (!(cliente.get("a_emision") + "").trim().isEmpty()) {

                                                            if (cliente.has("clave_unica")) {

                                                                if (!(cliente.get("clave_unica") + "").trim().isEmpty()) {

                                                                    if (cliente.has("n_emision")) {

                                                                        if (!(cliente.get("n_emision") + "").trim().isEmpty()) {

                                                                            if (cliente.has("clave_elector")) {

                                                                                if (!(cliente.get("clave_elector") + "").trim().isEmpty()) {

                                                                                    if (cliente.has("codigo_identificacion")) {

                                                                                        if (!(cliente.get("codigo_identificacion") + "").trim().isEmpty()) {

                                                                                            if (cliente.has("rostro1")) {

                                                                                                if (!(cliente.get("rostro1") + "").trim().isEmpty()) {

                                                                                                    return 0;

                                                                                                } else {

                                                                                                    return 12;

                                                                                                }

                                                                                            } else {

                                                                                                return 12;

                                                                                            }

                                                                                        } else {

                                                                                            return 11;

                                                                                        }

                                                                                    } else {

                                                                                        return 11;

                                                                                    }

                                                                                } else {

                                                                                    return 10;

                                                                                }

                                                                            } else {

                                                                                return 10;

                                                                            }

                                                                        } else {

                                                                            return 9;

                                                                        }

                                                                    } else {

                                                                        return 9;

                                                                    }

                                                                } else {

                                                                    return 8;

                                                                }

                                                            } else {

                                                                return 8;

                                                            }

                                                        } else {

                                                            return 7;

                                                        }

                                                    } else {

                                                        return 7;

                                                    }

                                                } else {

                                                    return 6;

                                                }

                                            } else {

                                                return 6;

                                            }

                                        } else {

                                            return 5;

                                        }

                                    } else {

                                        return 5;

                                    }

                                } else {

                                    return 4;

                                }

                            } else {

                                return 4;

                            }

                        } else {

                            return 3;

                        }

                    } else {

                        return 3;

                    }

                } else {

                    return 2;

                }

            } else {

                return 2;

            }

        } else {

            return 1;

        }

    }

    public Integer validar_peticiones(Integer permitidas, Integer realizadas) {

        realizadas = realizadas + 1;

        if (realizadas <= permitidas) {

            return 0;

        } else {

            return 1;

        }

    }

}
