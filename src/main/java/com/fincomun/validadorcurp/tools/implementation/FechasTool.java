package com.fincomun.validadorcurp.tools.implementation;


import com.fincomun.validadorcurp.tools.definition.IFechasTool;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.GregorianCalendar;

@Validated
@Component("simpleFechasTool")
public class FechasTool implements IFechasTool {
    @Override
    public XMLGregorianCalendar fechaActualXmlGregorian() throws DatatypeConfigurationException {
        return fechaToXmlGregorian(fechaActual());
    }

    @Override
    public XMLGregorianCalendar fechaToXmlGregorian(Date fecha) throws DatatypeConfigurationException {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(fecha);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
    }

    @Override
    public XMLGregorianCalendar stringToXmlGregorian(String fecha, String formato) throws DatatypeConfigurationException, ParseException {
        return fechaToXmlGregorian(stringToDate(fecha,formato));
    }

    @Override
    public String fechaActual(String formato) {
        return dateToString(fechaActual(),formato);
    }

    @Override
    public String dateToString(Date fecha, String formato) {
        return new SimpleDateFormat(formato).format(fecha);
    }

    @Override
    public Date stringToDate(String fecha, String formato) throws ParseException {
        return new SimpleDateFormat(formato).parse(fecha);
    }

    @Override
    public Date fechaActual() {
        return Date.from(ZonedDateTime.now().toInstant());
    }

    @Override
    public String gcToStringFormated(XMLGregorianCalendar gregorianCalendar) {
        ZonedDateTime zonedDateTime = gregorianCalendar.toGregorianCalendar().toZonedDateTime();
        return zonedDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    @Override
    public String changeDateFormat(String fecha, String formatoOriginal, String formatoDeseado) throws ParseException {
        return  dateToString(stringToDate(fecha, formatoOriginal), formatoDeseado);
    }

}
