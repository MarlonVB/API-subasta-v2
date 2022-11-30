package com.examcomplexivo.subastainversaservices.security.service.usuario;

import com.examcomplexivo.subastainversaservices.security.entity.Usuario;
import com.examcomplexivo.subastainversaservices.security.repository.UsuarioRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
// * Mantiene la coherencia en mi BD
// * Si hay varias peticiones hacia una misma tabla y ocurre un error realizar un rollback
@Transactional
public class UsuarioServiceImp implements UsuarioService{

    @Autowired
    private UsuarioRespository usuarioRespository;

    @Override
    public List<Usuario> listar() {
        return (List<Usuario>) usuarioRespository.findAll();
    }

    @Override
    public Usuario findById(Long id) {
        return usuarioRespository.findById(id).orElse(new Usuario());
    }

    @Override
    public Optional<Usuario> findByNombreUsuario(String nombreUsuario) {
        return usuarioRespository.findByNombreUsuario(nombreUsuario);
    }

    @Override
    public boolean existsByNombreUsuario(String nombreUsuario) {
        return usuarioRespository.existsByNombreUsuario(nombreUsuario);
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        return usuarioRespository.save(usuario);
    }

    @Override
    public void eliminar(Long id) {
        usuarioRespository.deleteById(id);
    }
}
