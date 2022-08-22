package com.leancoder.horario.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/app")
public class AppController {

    @Value("${config.horario.apertura}")
    private Integer apertura;

    @Value("${config.horario.cierre}")
    private Integer cierre;
    
    @GetMapping({"", "/"})
    public String Index(Model model) {
        model.addAttribute("titulo", "Pagina de inicio");
        return "index";
    }

    @GetMapping("/cerrado")
    public String Closed(HttpServletRequest request, Model model) {
        StringBuilder mensaje = new StringBuilder("Horario de atencion: Desde las ");
            mensaje.append(apertura);
            mensaje.append("hrs. hasta las ");
            mensaje.append(cierre);
            mensaje.append("hrs.");
        model.addAttribute("mensaje", mensaje);
        return "cerrado";
    }

}
