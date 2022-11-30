package com.examcomplexivo.subastainversaservices.security.service;

import com.examcomplexivo.subastainversaservices.security.entity.Usuario;
import com.examcomplexivo.subastainversaservices.security.entity.UsuarioPrincipal;
import com.examcomplexivo.subastainversaservices.security.service.usuario.UsuarioService;
import com.examcomplexivo.subastainversaservices.security.service.usuario.UsuarioServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsersDetailsImp implements UserDetailsService {

    @Autowired
    UsuarioServiceImp usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.findByNombreUsuario(username).get();
        return UsuarioPrincipal.build(usuario);
    }
}
