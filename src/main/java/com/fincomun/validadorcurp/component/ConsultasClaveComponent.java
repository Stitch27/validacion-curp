package com.fincomun.validadorcurp.component;

import java.util.List;
import java.sql.ResultSet;
import java.sql.Connection;
import java.util.ArrayList;
import oracle.jdbc.OracleTypes;
import lombok.extern.slf4j.Slf4j;
import java.sql.CallableStatement;
import org.springframework.stereotype.Component;
import com.fincomun.validadorcurp.utilities.ConexionUtilities;
import com.fincomun.validadorcurp.utilities.PropiedadesUtilities;

@Slf4j
@Component
public class ConsultasClaveComponent extends ConexionUtilities {

    public Integer portal_valido(Integer identificador, String nombre) {

        CallableStatement declaracion = null;
        Connection conexion = abrir_conexion();

        if (conexion != null) {

            String consulta = "BEGIN " + PropiedadesUtilities.PAQUETE_PROCEDIMIENTOS + "." + PropiedadesUtilities.PROCEDIMIENTO_VALIDARPORTAL + "(?, ?, ?); END;";

            try {

                declaracion = conexion.prepareCall(consulta);

                declaracion.setInt(1, identificador);
                declaracion.setString(2, nombre);
                declaracion.registerOutParameter(3, OracleTypes.NUMBER);

                declaracion.execute();

                Integer resultado = declaracion.getInt(3);

                Integer finalizar = cerrar_conexion(declaracion, conexion);

                if (finalizar == 0) {

                    return resultado;

                } else {

                    return 5;

                }

            } catch (Exception e) {

                cerrar_conexion(declaracion, conexion);

                log.error("EXCEPCION AL VALIDAR PORTAL");
                log.error("");
                log.error(e.getMessage());
                log.error("");
                log.error("");

                return 4;

            }

        } else {

            return 3;

        }

    }

    public Integer registrar_bitacora(String solicitud, String respuesta, Integer estado) {

        CallableStatement declaracion = null;
        Connection conexion = abrir_conexion();

        if (conexion != null) {

            String consulta = "BEGIN " + PropiedadesUtilities.PAQUETE_PROCEDIMIENTOS + "." + PropiedadesUtilities.PROCEDIMIENTO_REGISTRARBITACORA + "(?, ?, ?, ?, ?); END;";

            try {

                declaracion = conexion.prepareCall(consulta);

                declaracion.setString(1, solicitud);
                declaracion.setString(2, respuesta);
                declaracion.setInt(3, estado);
                declaracion.registerOutParameter(4, OracleTypes.NUMBER);
                declaracion.registerOutParameter(5, OracleTypes.NUMBER);

                declaracion.execute();

                Integer resultado = declaracion.getInt(5);

                cerrar_conexion(declaracion, conexion);

                return resultado;

            } catch (Exception e) {

                cerrar_conexion(declaracion, conexion);

                log.error("EXCEPCION AL REGISTRAR EN BITACORA");
                log.error("");
                log.error(e.getMessage());
                log.error("");
                log.error("");

                return null;

            }

        } else {

            return null;

        }

    }

    public Integer registrar_solicitud(String nombre_portal, Integer identificador_portal, String cnombre, String apaterno,
            String amaterno, String reconocimiento, String aregistro, String aemision, String cclave, String nemision, String celector, String cidentificador,
            Integer bidentificador, String rostro1) {

        CallableStatement declaracion = null;
        Connection conexion = abrir_conexion();

        if (conexion != null) {

            String consulta = "BEGIN " + PropiedadesUtilities.PAQUETE_PROCEDIMIENTOS + "." + PropiedadesUtilities.PROCEDIMIENTO_REGISTRARSOLICITUD + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); END;";

            try {

                declaracion = conexion.prepareCall(consulta);

                declaracion.setString(1, nombre_portal);
                declaracion.setInt(2, identificador_portal);
                declaracion.setString(3, cnombre);
                declaracion.setString(4, apaterno);
                declaracion.setString(5, amaterno);
                declaracion.setString(6, reconocimiento);
                declaracion.setString(7, aregistro);
                declaracion.setString(8, aemision);
                declaracion.setString(9, cclave);
                declaracion.setString(10, nemision);
                declaracion.setString(11, celector);
                declaracion.setString(12, cidentificador);
                declaracion.setInt(13, bidentificador);
                declaracion.setString(14, rostro1);
                declaracion.registerOutParameter(15, OracleTypes.NUMBER);
                declaracion.registerOutParameter(16, OracleTypes.NUMBER);

                declaracion.execute();

                Integer resultado = declaracion.getInt(16);

                cerrar_conexion(declaracion, conexion);

                return resultado;

            } catch (Exception e) {

                cerrar_conexion(declaracion, conexion);

                log.error("EXCEPCION AL REGISTRAR SOLICITUD");
                log.error("");
                log.error(e.getMessage());
                log.error("");
                log.error("");

                return null;

            }

        } else {

            return null;

        }

    }

    public Integer registrar_servicio(String codigo, String descripcion, String respuesta, String solicitud, String resultados, Integer solicitud_identificador) {

        CallableStatement declaracion = null;
        Connection conexion = abrir_conexion();

        if (conexion != null) {

            String consulta = "BEGIN " + PropiedadesUtilities.PAQUETE_PROCEDIMIENTOS + "." + PropiedadesUtilities.PROCEDIMIENTO_REGISTRARSERVICIO + "(?, ?, ?, ?, ?, ?, ?); END;";

            try {

                declaracion = conexion.prepareCall(consulta);

                declaracion.setString(1, codigo);
                declaracion.setString(2, descripcion);
                declaracion.setString(3, respuesta);
                declaracion.setString(4, solicitud);
                declaracion.setString(5, resultados);
                declaracion.setInt(6, solicitud_identificador);
                declaracion.registerOutParameter(7, OracleTypes.NUMBER);

                declaracion.execute();

                Integer resultado = declaracion.getInt(7);

                cerrar_conexion(declaracion, conexion);

                return resultado;

            } catch (Exception e) {

                cerrar_conexion(declaracion, conexion);

                log.error("EXCEPCION AL REGISTRAR SERVICIO");
                log.error("");
                log.error(e.getMessage());
                log.error("");
                log.error("");

                return null;

            }

        } else {

            return null;

        }

    }

    private Integer cerrar_conexion(CallableStatement declaracion, Connection conexion) {

        try {

            conexion.close();
            declaracion.close();

            return 0;

        } catch (Exception e) {

            log.error("EXEPCION AL CERRAR LA CONEXION DE BASE DE DATOS");
            log.error("");
            log.error(e.getMessage());
            log.error("");
            log.error("");

            return 1;

        }

    }

    private Connection abrir_conexion() {

        try {

            return this.conexion();

        } catch (Exception e) {

            log.error("EXEPCION AL ABRIR LA CONEXION DE BASE DE DATOS");
            log.error("");
            log.error(e.getMessage());
            log.error("");
            log.error("");

            return null;

        }

    }

    public List<Integer> obtener_configuracion(Integer identificador, String institucion, Integer estatus) {

        CallableStatement declaracion = null;
        Connection conexion = abrir_conexion();

        List<Integer> parametros = new ArrayList<>();

        if (conexion != null) {

            String consulta = "BEGIN " + PropiedadesUtilities.PAQUETE_PROCEDIMIENTOS + "." + PropiedadesUtilities.PROCEDIMIENTO_OBTENERCONFIGURACION + "(?, ?, ?, ?); END;";

            try {

                declaracion = conexion.prepareCall(consulta);

                declaracion.setInt(1, identificador);
                declaracion.setString(2, institucion);
                declaracion.setInt(3, estatus);
                declaracion.registerOutParameter(4, OracleTypes.CURSOR);

                declaracion.execute();

                parametros.add(0);
                ResultSet registros = (ResultSet) declaracion.getObject(4);

                while (registros.next()) {

                    parametros.add(registros.getInt("PETICIONES_PERMITIDAS_NUM"));
                    parametros.add(registros.getInt("PETICIONES_REALIZADAS_E_NUM"));
                    parametros.add(registros.getInt("PETICIONES_REALIZADAS_F_NUM"));

                }

                Integer finalizar = cerrar_conexion(declaracion, conexion);

                if (finalizar == 0) {

                    return parametros;

                } else {

                    parametros.add(3);

                    return parametros;

                }

            } catch (Exception e) {

                cerrar_conexion(declaracion, conexion);

                log.error("EXCEPCION AL CONSULTAR EL NUMERO DE PETICIONES");
                log.error("");
                log.error(e.getMessage());
                log.error("");
                log.error("");

                parametros.add(2);

                return parametros;

            }

        } else {

            parametros.add(1);

            return parametros;

        }

    }

    public Integer actualizar_peticiones(Integer proceso, Integer identificador) {

        CallableStatement declaracion = null;
        Connection conexion = abrir_conexion();

        if (conexion != null) {

            String consulta = "BEGIN " + PropiedadesUtilities.PAQUETE_PROCEDIMIENTOS + "." + PropiedadesUtilities.PROCEDIMIENTO_ACTUALIZARPETICIONES + "(?, ?, ?); END;";

            try {

                declaracion = conexion.prepareCall(consulta);

                declaracion.setInt(1, proceso);
                declaracion.setInt(2, identificador);
                declaracion.registerOutParameter(3, OracleTypes.NUMBER);

                declaracion.execute();

                Integer resultado = declaracion.getInt(3);

                Integer finalizar = cerrar_conexion(declaracion, conexion);

                if (finalizar == 0) {

                    return resultado;

                } else {

                    return 4;

                }

            } catch (Exception e) {

                cerrar_conexion(declaracion, conexion);

                log.error("EXCEPCION AL ACTUALIZAR PETICIONES");
                log.error("");
                log.error(e.getMessage());
                log.error("");
                log.error("");

                return 3;

            }

        } else {

            return 2;

        }

    }

    public Integer registrar_bitacora_renapo(String solicitud, String respuesta, Integer estado) {

        Connection conexion = abrir_conexion();

        if (conexion != null) {

            String consulta = "BEGIN " + PropiedadesUtilities.PAQUETE_PROCEDIMIENTOS + "." + PropiedadesUtilities.PROCEDIMIENTO_BITACORA_RENAPO + "(?, ?, ?, ?, ?); END;";

            try {

                CallableStatement declaracion = conexion.prepareCall(consulta);

                declaracion.setString(1, solicitud);
                declaracion.setString(2, respuesta);
                declaracion.setInt(3, estado);
                declaracion.registerOutParameter(4, OracleTypes.NUMBER);
                declaracion.registerOutParameter(5, OracleTypes.NUMBER);

                declaracion.execute();

                Integer resultado = declaracion.getInt(5);

                cerrar_conexion(declaracion, conexion);

                return resultado;

            } catch (Exception e) {

                return null;

            }

        } else {

            return null;

        }

    }

    public Integer registrar_solicitud_renapo(String nombre_portal, Integer identificador_portal, String cclave, Integer bidentificador) {

        Connection conexion = abrir_conexion();

        if (conexion != null) {

            String consulta = "BEGIN " + PropiedadesUtilities.PAQUETE_PROCEDIMIENTOS + "." + PropiedadesUtilities.PROCEDIMIENTO_SOLICITUD_RENAPO + "(?, ?, ?, ?, ?, ?); END;";

            try {

                CallableStatement declaracion = conexion.prepareCall(consulta);

                declaracion.setString(1, nombre_portal);
                declaracion.setInt(2, identificador_portal);
                declaracion.setString(3, cclave);
                declaracion.setInt(4, bidentificador);
                declaracion.registerOutParameter(5, OracleTypes.NUMBER);
                declaracion.registerOutParameter(6, OracleTypes.NUMBER);

                declaracion.execute();

                Integer resultado = declaracion.getInt(6);

                cerrar_conexion(declaracion, conexion);

                return resultado;

            } catch (Exception e) {

                return null;

            }

        } else {

            return null;

        }

    }

    public Integer registrar_servicio_renapo(String codigo, String descripcion, String respuesta, String solicitud, String resultados, Integer solicitud_identificador) {

        Connection conexion = abrir_conexion();

        if (conexion != null) {

            String consulta = "BEGIN " + PropiedadesUtilities.PAQUETE_PROCEDIMIENTOS + "." + PropiedadesUtilities.PROCEDIMIENTO_SERVICIO_RENAPO + "(?, ?, ?, ?, ?, ?, ?); END;";

            try {

                CallableStatement declaracion = conexion.prepareCall(consulta);

                declaracion.setString(1, codigo);
                declaracion.setString(2, descripcion);
                declaracion.setString(3, respuesta);
                declaracion.setString(4, solicitud);
                declaracion.setString(5, resultados);
                declaracion.setInt(6, solicitud_identificador);
                declaracion.registerOutParameter(7, OracleTypes.NUMBER);

                declaracion.execute();

                Integer resultado = declaracion.getInt(7);

                cerrar_conexion(declaracion, conexion);

                return resultado;

            } catch (Exception e) {

                return null;

            }

        } else {

            return null;

        }

    }

    // Modificacion para obtener parametro
    public String obtener_parametro(String clave) {

        String parametro = "";
        CallableStatement declaracion = null;
        Connection conexion = abrir_conexion();

        if (conexion != null) {

            String consulta = "BEGIN " + PropiedadesUtilities.PAQUETE_PROCEDIMIENTOS + "." + PropiedadesUtilities.PROCEDIMIENTO_OBTENER_PARAMETRO + "(?, ?); END;";

            try {

                declaracion = conexion.prepareCall(consulta);

                declaracion.setString(1, clave);
                declaracion.registerOutParameter(2, OracleTypes.CURSOR);

                declaracion.execute();
                ResultSet registros = (ResultSet) declaracion.getObject(2);

                while (registros.next()) {

                    parametro = registros.getString("VALOR_VAR");

                }

                Integer finalizar = cerrar_conexion(declaracion, conexion);

                if (finalizar == 0) {

                    return parametro;

                } else {

                    return parametro;

                }

            } catch (Exception e) {

                cerrar_conexion(declaracion, conexion);

                log.error("EXCEPCION AL OBTENER PARAMETRO");
                log.error("");
                log.error(e.getMessage());
                log.error("");
                log.error("");

                return parametro;

            }

        } else {

            return parametro;

        }

    }

}
