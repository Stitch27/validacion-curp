package com.fincomun.identificacion.utilities;

import java.util.Hashtable;
import javax.naming.Context;
import javax.sql.DataSource;
import javax.naming.InitialContext;

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

            return null;

        }

    }

}
