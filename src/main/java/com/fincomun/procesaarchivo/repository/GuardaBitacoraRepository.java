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
public class GuardaBitacoraRepository extends StoredProcedure {

    private static final String NOMBRE_PROCEDIMIENTO = PropiedadesUtilities.PAQUETE_PROCEDIMIENTOS + "." + PropiedadesUtilities.PROCEDIMIENTO_BITACORA_RENAPO;
    
    @Autowired
    public GuardaBitacoraRepository(@Qualifier("dataSourceOracle") DataSource dataSource) {
        super(dataSource, NOMBRE_PROCEDIMIENTO);
        declareParameter(new SqlParameter("e_solicitud", Types.CLOB));
        declareParameter(new SqlParameter("e_respuesta", Types.CLOB));
        declareParameter(new SqlParameter("e_estado", Types.INTEGER));
        declareParameter(new SqlOutParameter("s_codigo", Types.INTEGER));
        declareParameter(new SqlOutParameter("s_identificador", Types.INTEGER));
        compile();
    }
    
    public Integer guardarBitacora(String solicitud, String respuesta, Integer estado) {
        Integer identificador = null;
        try {
            Map<String, Object> in = new HashMap<String, Object>();
            in.put("e_solicitud", solicitud);
            in.put("e_respuesta", respuesta);
            in.put("e_estado", estado);
            
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
            log.error("EXCEPCION AL REGISTRAR BITACORA");
            log.error("");
            log.error(e.getMessage());
            log.error("");
            log.error("");
            return -1;
        }
    }
}
