package com.fincomun.validadorcurp.tools.definition;


import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nahum Rodriguez
 * */
@Validated
public interface IObjectConvertor {

    /***
     * @param <T> Clase del objeto que se desea convertir a {@link HashMap}
     * @param object objecto a convertir a {@link HashMap}
     * @return retorna el objeto proporcionado en un {@link HashMap } {@link String} , {@link Object}
     * */
    @NotNull <T> HashMap<String, Object> objectToMapConverter (@NotNull T object);

    /**
     * @param <T> Clase del objeto que se desea convertir a {@link HashMap}
     * @param object objecto a convertir a {@link HashMap}
     * @return retorna el objeto proporcionado en un {@link HashMap} {@link String}, {@link String}
     * */
    <T>HashMap<String, String> objectToMapConverterSS (@NotNull T object);

    /**
     * @param <T> Clase del objeto que se desea convertir a json
     * @param object objecto a convertir a json
     * @return retorna json del objeto proporcionado en {@link String}
     * */
    <T> String objectToJsonConverter (@NotNull T object) ;

    /**
     * @param <T> Clase del objeto que se desea obtener
     * @param map {@link Map} {@link String} , {@link Object} que se desea convertir a objeto de la clase indicada
     * @param classTo Clase del objeto que se desea obtener
     * @return retorna objeto T parseado en base al {@link Map} proporcionado
     * **/
    <T> T mapToObjectConverter (@NotNull Map<String, Object> map , @NotNull Class<T> classTo);

    /**
     * @param <T> Clase del objeto que se desea obtener
     * @param json json en {@link String} que se desea convertir al objeto indicado
     * @param classTo Clase del objeto que se desea obtener
     * @return retorna objeto T parseado en base al json proporcionado
     * **/
    <T> T jsonToObjectConverter (@NotNull @NotEmpty String json ,@NotNull Class<T> classTo);


    HashMap<String , Object> jsonToMap(@NotEmpty String json);
}
