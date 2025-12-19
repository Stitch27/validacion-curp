package com.fincomun.validadorcurp.tools.definition;


import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.ParseException;
import java.util.Date;

/**
 * @author Nahum Rodriguez
 * */

@Validated
public interface IFechasTool {

    public static final String DD_MM_YY = "dd-MM-yy";
    public static final String DD_MM_YYYY = "dd-MM-yyyy";
    public static final String MM_DD_YYYY = "MM-dd-yyyy";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HH_MM_SS ="yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM_SS_SSS ="yyyy-MM-dd HH:mm:ss.SSS";
    public static final String YYYY_MM_DD_HH_MM_SS_SSSZ ="yyyy-MM-dd HH:mm:ss.SSSZ";
    public static final String YYYYMMDD = "yyyyMMdd";

    /**
     * @return Objecto XMLGregorianCalendar con la fecha actual
     * @throws DatatypeConfigurationException
     * */
    @NotNull XMLGregorianCalendar fechaActualXmlGregorian() throws DatatypeConfigurationException;

    /**
     * @param fecha Date con la fecha a convertir
     * @return  Objecto XMLGregorianCalendar con la fecha proporcionada
     * @throws DatatypeConfigurationException
     * */
    @NotNull XMLGregorianCalendar fechaToXmlGregorian(@NotNull Date fecha) throws DatatypeConfigurationException;

    /**
     * @param fecha String con la fecha a convertir
     * @param  formato String con el formato en el que se encuentra la fecha
     * @return  Objecto XMLGregorianCalendar con la fecha proporcionada
     * @throws DatatypeConfigurationException
     * @throws ParseException
     * */
    @NotNull XMLGregorianCalendar stringToXmlGregorian(@NotNull @NotBlank String fecha, @NotNull @NotBlank String formato) throws DatatypeConfigurationException, ParseException;

    /**
     * @param formato formato en que se desea obtener la fecha
     * @return fecha en formato indicado
     * */
    @NotNull String fechaActual(@NotNull @NotBlank String formato);

    /**
     * @param fecha Objeto Date a convertir
     * @param formato String con el formato de la fecha
     * @return fecha en formato indicado
     * */
    @NotNull String dateToString(@NotNull Date fecha, @NotNull @NotBlank String formato);

    /**
     * @param fecha fecha en formato indicado
     * @param formato proporcionado para la fecha
     * @return Objeto Date generado con la @fecha proporcionada
     * @throws ParseException
     * */
    @NotNull Date stringToDate(@NotNull @NotBlank String fecha, @NotNull @NotBlank String formato) throws ParseException;


    /**
     * @return Objeto Date con la fecha actual
     * */
    @NotNull Date fechaActual();

    /**
     * @param gregorianCalendar objeto {@link XMLGregorianCalendar} del que se desea obtener su timestamp en formato de string
     * @return timestamp del {@link XMLGregorianCalendar} proporcionado
     * */
    @NotNull String gcToStringFormated(@NotNull XMLGregorianCalendar gregorianCalendar);


    @NotNull String changeDateFormat(@NotBlank String fecha, @NotNull String formatoOriginal, @NotNull String formatoDeseado) throws ParseException;

}
