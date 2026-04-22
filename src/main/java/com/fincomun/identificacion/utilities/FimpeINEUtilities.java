package com.fincomun.identificacion.utilities;

import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import com.fincomun.identificacion.model.RequestIdentificacionModel;

@Component
public class FimpeINEUtilities {

    public Integer validar_portal(RequestIdentificacionModel solicitud) {

        if (Objects.nonNull(solicitud.getDatos_portal())) {

            if (Objects.nonNull(solicitud.getDatos_portal().getIdentificador())) {

                if (!solicitud.getDatos_portal().getIdentificador().trim().isEmpty()) {

                    if (StringUtils.isNumeric(solicitud.getDatos_portal().getIdentificador().trim())) {

                        if (Objects.nonNull(solicitud.getDatos_portal().getNombre())) {

                            if (!solicitud.getDatos_portal().getNombre().trim().isEmpty()) {

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

    public Integer validar_cliente(RequestIdentificacionModel solicitud) {

        if (Objects.nonNull(solicitud.getDatos_cliente())) {

            if (Objects.nonNull(solicitud.getDatos_cliente().getNombre())) {

                if (!solicitud.getDatos_cliente().getNombre().trim().isEmpty()) {

                    if (Objects.nonNull(solicitud.getDatos_cliente().getApellido_paterno())) {

                        if (!solicitud.getDatos_cliente().getApellido_paterno().trim().isEmpty()) {

                            if (Objects.nonNull(solicitud.getDatos_cliente().getApellido_materno())) {

                                if (!solicitud.getDatos_cliente().getApellido_materno().isEmpty()) {

                                    if (Objects.nonNull(solicitud.getDatos_cliente().getReconocimiento_optico())) {

                                        if (!solicitud.getDatos_cliente().getReconocimiento_optico().isEmpty()) {

                                            if (Objects.nonNull(solicitud.getDatos_cliente().getA_registro())) {

                                                if (!solicitud.getDatos_cliente().getA_registro().isEmpty()) {

                                                    if (Objects.nonNull(solicitud.getDatos_cliente().getA_emision())) {

                                                        if (!solicitud.getDatos_cliente().getA_emision().isEmpty()) {

                                                            if (Objects.nonNull(solicitud.getDatos_cliente().getClave_unica())) {

                                                                if (!solicitud.getDatos_cliente().getClave_unica().isEmpty()) {

                                                                    if (Objects.nonNull(solicitud.getDatos_cliente().getN_emision())) {

                                                                        if (!solicitud.getDatos_cliente().getN_emision().isEmpty()) {

                                                                            if (Objects.nonNull(solicitud.getDatos_cliente().getClave_elector())) {

                                                                                if (!solicitud.getDatos_cliente().getClave_elector().isEmpty()) {

                                                                                    if (Objects.nonNull(solicitud.getDatos_cliente().getCodigo_identificacion())) {

                                                                                        if (!solicitud.getDatos_cliente().getCodigo_identificacion().isEmpty()) {

                                                                                            return 0;

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
