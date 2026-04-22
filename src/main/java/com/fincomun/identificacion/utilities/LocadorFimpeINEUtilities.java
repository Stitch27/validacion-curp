package com.fincomun.identificacion.utilities;

import java.util.Hashtable;
import javax.naming.Context;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import javax.naming.InitialContext;

@Slf4j
public class LocadorFimpeINEUtilities {

    private static LocadorFimpeINEUtilities instancia;

    private LocadorFimpeINEUtilities() {

    }

    public static LocadorFimpeINEUtilities obtener_instancia() {

        if (instancia == null) {

            instancia = new LocadorFimpeINEUtilities();

        }

        return instancia;

    }

    public DataSource informacion() {

        try {

            Hashtable tabla = new Hashtable();

            tabla.put(Context.SECURITY_PRINCIPAL, PropiedadesFimpeINEUtilities.USBADA);
            tabla.put(Context.SECURITY_CREDENTIALS, PropiedadesFimpeINEUtilities.COBADA);

            InitialContext contexto = new InitialContext(tabla);

            return (DataSource) contexto.lookup("jdbc/Oracle");

        } catch (Exception e) {

            log.error("EXCEPCION AL CONSULTAR LA INFORMACION DE LA BASE DE DATOS ", e);
            return null;

        }

    }

}
