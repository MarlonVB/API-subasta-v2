package com.examcomplexivo.subastainversaservices.security.jwt;

import com.examcomplexivo.subastainversaservices.security.entity.UsuarioPrincipal;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

// * Genera el token y valida que este bien formado, que no este expirado y demas
@Component
public class JwtProvider {

    // * Se utiliza en desarrollo para ver que metodo es el que nos esta dando error
    private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwl.expiration}")
    private int expiration;

    // * Generamos el token
    public String generateToken(Authentication authentication){
        UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) authentication.getPrincipal();
        return Jwts.builder().setSubject(usuarioPrincipal.getUsername())
                .setIssuedAt(new Date()) // fecha de creaccion del token
                .setExpiration(new Date(new Date().getTime() + expiration * 1000)) // fecha de expiracion del token
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    // * Obtenemos el nombre del usuario a partir del token
    public String getNombreUsuarioFromToken(String token){
        // * getSubject es el nombre del usuario
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    // * Validamos el token
    public boolean validateToken(String token){
       try{
           Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
           return true;
       }catch (MalformedJwtException e){
           logger.error("Token mal construido");
       }catch (UnsupportedJwtException e){
           logger.error("Token no soportado");
       }catch (ExpiredJwtException e){
           logger.error("Token expirado");
       }catch (IllegalArgumentException e){
           logger.error("Token vacio");
       }catch (SignatureException e){
           logger.error("Fallo en la firma");
       }
       return false;
    }

}
