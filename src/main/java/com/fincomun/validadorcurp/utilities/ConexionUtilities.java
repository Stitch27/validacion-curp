package com.fincomun.validadorcurp.utilities;

import java.sql.Connection;
import javax.sql.DataSource;

public class ConexionUtilities {

    public Connection conexion() {

        try {

            DataSource conexion = LocadorUtilities.obtener_instancia().informacion();

            return conexion.getConnection();

        } catch (Exception e) {

            return null;

        }

    }

}
