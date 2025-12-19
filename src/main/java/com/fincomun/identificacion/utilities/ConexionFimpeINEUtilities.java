package com.fincomun.identificacion.utilities;

import java.sql.Connection;
import javax.sql.DataSource;

public class ConexionFimpeINEUtilities {

    public Connection conexion() {

        try {

            DataSource conexion = LocadorFimpeINEUtilities.obtener_instancia().informacion();

            return conexion.getConnection();

        } catch (Exception e) {

            return null;

        }

    }

}
