package com.examcomplexivo.subastainversaservices.security.jwt;

import com.examcomplexivo.subastainversaservices.security.service.UsersDetailsImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// * Se ejecuta por cada peticion
// * Valida el token a traves de la clase JwtProvider
// * Si es valido el token permite el acceso caso contrario lanza un exepcion
public class JwtTokenFilter extends OncePerRequestFilter {

    private final static Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

    // * Inyectamos
    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    UsersDetailsImp usersDetailsImp;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String token = this.getToken(request);
            // * Primero validamos que el token obtenido exista y sea valido
            if (token != null && jwtProvider.validateToken(token)) {
                // * Obtenemos el usuario a partir del token
                String nombreUsuario = jwtProvider.getNombreUsuarioFromToken(token);
                // * Creamos un userdatails
                UserDetails userDetails = usersDetailsImp.loadUserByUsername(nombreUsuario);
                // * Autenticamos al usuario
                UsernamePasswordAuthenticationToken auth = new
                        UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                // * Una ves autenticado al contexto de autenticacion se pasamos el auth
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }catch (Exception e){
            logger.error("Error en el medoto doFilterInternal");
        }
        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer")){
            return header.replace("Bearer ", "");
        }else {
            return null;
        }
    }
}
