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
public class GuardaServicioRepository extends StoredProcedure {
    
    private static final String NOMBRE_PROCEDIMIENTO = PropiedadesUtilities.PAQUETE_PROCEDIMIENTOS + "." + PropiedadesUtilities.PROCEDIMIENTO_SERVICIO_RENAPO;
    
    @Autowired
    public GuardaServicioRepository(@Qualifier("dataSourceOracle") DataSource dataSource) {
        super(dataSource, NOMBRE_PROCEDIMIENTO);

        declareParameter(new SqlParameter("e_codigo", Types.VARCHAR));
        declareParameter(new SqlParameter("e_descripcion", Types.VARCHAR));
        declareParameter(new SqlParameter("e_respuesta", Types.VARCHAR));
        declareParameter(new SqlParameter("e_solicitud", Types.CLOB));
        declareParameter(new SqlParameter("e_resultado", Types.CLOB));
        declareParameter(new SqlParameter("e_solicitud_identificador", Types.INTEGER));
        declareParameter(new SqlOutParameter("s_codigo", Types.INTEGER));
        compile();
    }

    public Integer guardarServicio(String codigo, String descripcion, String respuesta, String solicitud, String resultado, Integer solicitudIdentificador) {
        Integer codigoSP = null;
        try {
            Map<String, Object> in = new HashMap<String, Object>();
            in.put("e_codigo", codigo);
            in.put("e_descripcion", descripcion);
            in.put("e_respuesta", respuesta);
            in.put("e_solicitud", solicitud);
            in.put("e_resultado", resultado);
            in.put("e_solicitud_identificador", solicitudIdentificador);
            
            log.info("Execute SP ... " + NOMBRE_PROCEDIMIENTO);
            // log.info(in.toString());

            Map<String, Object> out = super.execute(in);

            codigoSP = (Integer) out.get("s_codigo");

            if (codigoSP == 1) {
                return -1;
            }
            return codigoSP;
        } catch (Exception e) {
            log.error("EXCEPCION AL REGISTRAR SERVICIO");
            log.error("");
            log.error(e.getMessage());
            log.error("");
            log.error("");
            return -1;
        }
    }
}
