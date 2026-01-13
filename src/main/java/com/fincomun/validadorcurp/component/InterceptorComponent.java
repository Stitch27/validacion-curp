package com.fincomun.validadorcurp.component;

import org.slf4j.MDC;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class InterceptorComponent implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest solicitud, HttpServletResponse respuesta, Object manejador) {

        String transaccion = solicitud.getHeader("transaccion");

        if (transaccion == null || transaccion.trim().isEmpty()) {

            String fecha = new SimpleDateFormat("ddMMyyyy").format(new Date());
            String hora = new SimpleDateFormat("HHmmss").format(new Date());

            transaccion = "TRANS." + fecha + "." + hora;

        }

        solicitud.setAttribute("transaccion", transaccion);
        MDC.put("transaccion", transaccion);

        return true;

    }

    @Override
    public void afterCompletion(HttpServletRequest solicitud, HttpServletResponse respuesta, Object manejador, Exception excepcion) {

        MDC.clear();

    }

}
