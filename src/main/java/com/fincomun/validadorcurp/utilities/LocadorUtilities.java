package com.fincomun.validadorcurp.utilities;

import java.util.Hashtable;
import javax.naming.Context;
import javax.sql.DataSource;
import javax.naming.InitialContext;

public class LocadorUtilities {

    private static LocadorUtilities instancia;

    private LocadorUtilities() {

    }

    public static LocadorUtilities obtener_instancia() {

        if (instancia == null) {

            instancia = new LocadorUtilities();

        }

        return instancia;

    }

    public DataSource informacion() {

        try {

            Hashtable tabla = new Hashtable();

            tabla.put(Context.SECURITY_PRINCIPAL, PropiedadesUtilities.USBADA);
            tabla.put(Context.SECURITY_CREDENTIALS, PropiedadesUtilities.COBADA);

            InitialContext contexto = new InitialContext(tabla);

            return (DataSource) contexto.lookup("jdbc/Oracle");

        } catch (Exception e) {

            return null;

        }

    }

}
