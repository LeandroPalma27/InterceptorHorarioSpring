package com.leancoder.horario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    @Qualifier("horarioInterceptor")
    private HandlerInterceptor horarioInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // En caso que queramos aplicar a todos los endpoints del controlador, 
        // pero sin contar al endpoint se carga en caso de un false:
        registry.addInterceptor(horarioInterceptor)./* addPathPatterns("/app") */excludePathPatterns("/app/cerrado");
    }
    
}
