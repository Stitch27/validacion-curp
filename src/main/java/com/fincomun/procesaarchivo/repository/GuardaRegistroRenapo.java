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

import com.fincomun.procesaarchivo.dto.RegistraArchivoDto;
import com.fincomun.procesaarchivo.dto.RegistraArchivoSPResponse;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class GuardaRegistroRenapo extends StoredProcedure {

    private static final String NOMBRE_PROCEDIMIENTO = "PKG_MASIVOS_RENAPO.INSERTAR_REGISTRO";
    
    @Autowired
    public GuardaRegistroRenapo(@Qualifier("dataSourceOracle") DataSource dataSource) {
        super(dataSource, NOMBRE_PROCEDIMIENTO);

        declareParameter(new SqlParameter("p_sol_portal_nombre", Types.VARCHAR));
        declareParameter(new SqlParameter("p_sol_portal_id", Types.INTEGER));
        declareParameter(new SqlParameter("p_sol_cliente_curp", Types.VARCHAR));
        declareParameter(new SqlParameter("p_ser_codigo", Types.VARCHAR));
        declareParameter(new SqlParameter("p_ser_descripcion", Types.VARCHAR));
        declareParameter(new SqlParameter("p_ser_respuesta", Types.VARCHAR));
        declareParameter(new SqlParameter("p_bit_solicitud", Types.CLOB));
        declareParameter(new SqlParameter("p_bit_respuesta", Types.CLOB));
        declareParameter(new SqlParameter("p_estado", Types.INTEGER));
        declareParameter(new SqlOutParameter("p_codigo_out", Types.INTEGER));
        declareParameter(new SqlOutParameter("p_mensaje_out", Types.VARCHAR));
        compile();
    }

    public RegistraArchivoSPResponse guardarRegistroRenapo(RegistraArchivoDto dto) {
        RegistraArchivoSPResponse response = new RegistraArchivoSPResponse();
        try {
            Map<String, Object> in = new HashMap<>();
            in.put("p_sol_portal_nombre", "Masivos");
            in.put("p_sol_portal_id", 106);
            in.put("p_sol_cliente_curp", dto.getSolClienteCurp());
            in.put("p_ser_codigo", dto.getSerCodigo());
            in.put("p_ser_descripcion", dto.getSerDescripcion());
            in.put("p_ser_respuesta", dto.getSerRespuesta());
            in.put("p_bit_solicitud", dto.getBitSolicitud());
            in.put("p_bit_respuesta", dto.getBitRespuesta());
            in.put("p_estado", dto.getEstado());
            
            log.info("Execute SP ... " + NOMBRE_PROCEDIMIENTO);
            log.info(in.toString());

            Map<String, Object> out = super.execute(in);

            response.setCodigoOut((Integer) out.get("p_codigo_out"));
            response.setMensajeOut((String) out.get("p_mensaje_out"));

            return response;
        } catch (Exception e) {
            log.error("EXCEPCION AL GUARDAR REGISTRO");
            log.error("");
            log.error(e.getMessage());
            log.error("");
            log.error("");
            response.setCodigoOut(-1);
            response.setMensajeOut(e.getMessage());
            return response;
        }
    }
}
