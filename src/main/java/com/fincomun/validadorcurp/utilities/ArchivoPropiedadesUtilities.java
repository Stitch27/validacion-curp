package com.fincomun.validadorcurp.utilities;

import java.io.InputStream;
import java.util.Properties;
import java.io.FileInputStream;

public class ArchivoPropiedadesUtilities {

    static Properties propiedades = new Properties();

    private ArchivoPropiedadesUtilities() {

    }

    static {

        try {

            InputStream entrada = new FileInputStream("validadorcurp/conf/curp.properties");
            propiedades.load(entrada);

        } catch (Exception e) {

        }

    }

    public static String propiedad(String nombre) {

        String valor = propiedades.getProperty(nombre);

        if (valor != null) {

            valor = valor.trim();

        }

        return valor;

    }

}
