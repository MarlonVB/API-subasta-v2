package com.examcomplexivo.subastainversaservices.security.service.usuario;


import com.examcomplexivo.subastainversaservices.security.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    List<Usuario> listar();

    Usuario findById(Long id);
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);

    boolean existsByNombreUsuario(String nombreUsuario);

    Usuario guardar(Usuario usuario);

    public void eliminar(Long id);
}
