package com.leancoder.horario.interceptors;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component("horarioInterceptor")
public class HorarioInterceptor implements HandlerInterceptor {

    // Con value inyectamos informacion escrita en el application.properties:
    @Value("${config.horario.apertura}")
    private Integer apertura;

    @Value("${config.horario.cierre}")
    private Integer cierre;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        Calendar calendar = Calendar.getInstance();
        int hora = calendar.get(Calendar.HOUR_OF_DAY);

        if (hora >= apertura && hora <= cierre) {
            StringBuilder mensaje = new StringBuilder("Bienvenido al sistema, usted esta dentro del hoario de atencion ");
            mensaje.append("(Horario de atencion: Desde las ");
            mensaje.append(apertura);
            mensaje.append("hrs. hasta las ");
            mensaje.append(cierre);
            mensaje.append("hrs.)");

            // A traves del request pasamos informacion entre interceptores o metodos handler:
            request.setAttribute("mensaje", mensaje.toString());
            return true;
        }

        // Se usa response generalmente para indicar respuestas de fallo (si es en el prehandle):
        response.sendRedirect(request.getContextPath() + "/app/cerrado");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
            
        if (request.getMethod().equalsIgnoreCase("post")) {
            return;
        }
        
        if (handler instanceof HandlerMethod/* modelAndView != null */) {
            var mensaje = request.getAttribute("mensaje");
            modelAndView.addObject("horario", mensaje);
        }
                
    }
    
}
