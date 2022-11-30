package com.examcomplexivo.subastainversaservices.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// * Comprueba que haya un token valido sino devuelve un Error 401 Unauthorized
// * Rechaza todas las peticiones que no esten autenticadas
@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {

    // * Se utiliza en desarrollo para ver que metodo es el que nos esta dando error
    private final static Logger logger = LoggerFactory.getLogger(JwtEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        logger.error("Bug en el metodo commence");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Este usuario no esta autorizado para realizar esta accion");
    }
}
