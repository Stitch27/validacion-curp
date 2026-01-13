package com.fincomun.procesaarchivo.repository;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Repository;

import com.fincomun.validadorcurp.utilities.PropiedadesUtilities;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class GuardaSolicitudRepository extends StoredProcedure {

    private static final String NOMBRE_PROCEDIMIENTO = PropiedadesUtilities.PAQUETE_PROCEDIMIENTOS + "." + PropiedadesUtilities.PROCEDIMIENTO_SOLICITUD_RENAPO;
    
    @Autowired
    public GuardaSolicitudRepository(@Qualifier("dataSourceOracle") DataSource dataSource) {
        super(dataSource, NOMBRE_PROCEDIMIENTO);

        declareParameter(new SqlParameter("e_portal_nombre", Types.VARCHAR));
        declareParameter(new SqlParameter("e_portal_identificador", Types.INTEGER));
        declareParameter(new SqlParameter("e_cliente_clave", Types.VARCHAR));
        declareParameter(new SqlParameter("e_bitacora_identificador", Types.INTEGER));
        declareParameter(new SqlOutParameter("s_codigo", Types.INTEGER));
        declareParameter(new SqlOutParameter("s_identificador", Types.INTEGER));
        compile();
    }

    public Integer guardarSolicitud(String portalNombre, Integer portalIdentificador, String clienteClave, Integer bitacoraIdentificador) {
        Integer identificador = null;
        try {
            Map<String, Object> in = new HashMap<String, Object>();
            in.put("e_portal_nombre", portalNombre);
            in.put("e_portal_identificador", portalIdentificador);
            in.put("e_cliente_clave", clienteClave);
            in.put("e_bitacora_identificador", bitacoraIdentificador);
            
            log.info("Execute SP ... " + NOMBRE_PROCEDIMIENTO);
            // log.info(in.toString());

            Map<String, Object> out = super.execute(in);

            Integer codigo = (Integer) out.get("s_codigo");
            identificador = (Integer) out.get("s_identificador");

            if (codigo == 1) {
                return -1;
            }
            return identificador;
        } catch (Exception e) {
            log.error("EXCEPCION AL REGISTRAR SOLICITUD");
            log.error("");
            log.error(e.getMessage());
            log.error("");
            log.error("");
            return -1;
        }
    }
}
