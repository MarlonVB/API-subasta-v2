package com.examcomplexivo.subastainversaservices.controllers;


import com.examcomplexivo.subastainversaservices.security.entity.Usuario;
import com.examcomplexivo.subastainversaservices.security.service.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT,
        RequestMethod.DELETE })
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping("listar")
    public List<Usuario> listar() {
        return service.listar();
    }

    @PostMapping("crear")
    public ResponseEntity<?> crear(@Valid @RequestBody Usuario usuario, BindingResult result) {

        if (result.hasErrors()) {
            return validar(result);
        }
        Optional<Usuario> existUsuarioNombre = service.findByNombreUsuario(usuario.getNombreUsuario());
        /**
         * Validacion para que los usuarios no se repitan
         * **/
        if (existUsuarioNombre.isPresent()) {
            return ResponseEntity.badRequest().body(
                    Collections.singletonMap("mensaje", "Ya hay un usuario registrado con este nombre.")
            );
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.guardar(usuario));
    }

    @PutMapping("/editar/{idUsuario}")
    public ResponseEntity<?> editarSubasta(@PathVariable(name = "idUsuario", required = true) Long idUsuario,
                                           @Valid @RequestBody Usuario usuario, BindingResult result) {
        if (result.hasErrors()) {
            return validar(result);
        }

        try {
            Usuario usuarioBD = service.findById(idUsuario);
            Optional<Usuario> existUsuarioNombre = service.findByNombreUsuario(usuario.getNombreUsuario());
            if (usuarioBD != null) {

                /**
                 * Validacion para que no se repitan email de otro user al editar
                 * **/
                if (!existUsuarioNombre.get().equals(usuarioBD)
                        && existUsuarioNombre.get().getNombreUsuario().equalsIgnoreCase(usuario.getNombreUsuario())) {
                    return ResponseEntity.badRequest().body(
                            Collections.singletonMap("mensaje", "Este usiario ya se encuentra registrado.")
                    );
                }

                usuarioBD.setNombreUsuario(usuario.getNombreUsuario());
                usuarioBD.setContraseniaUsuario(usuario.getContraseniaUsuario());

                service.guardar(usuarioBD);
                return ResponseEntity.ok().body(Collections.singletonMap("mensaje", "Usuario modificado correctamente."));
            } else {
                return ResponseEntity.status(404).body(Collections.singletonMap("mensaje", "El usuario no existe."));
            }
        } catch (Exception ex) {
            Logger.getLogger(SubastaController.class.getName()).log(Level.SEVERE, "Imposible realizar cambios");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/eliminar/{idUsuario}")
    public ResponseEntity<?> eliminarSubasta(@PathVariable(name = "idUsuario", required = true) Long idUsuario) {
        try {
            if (service.findById(idUsuario) != null) {
                service.eliminar(idUsuario);
                return ResponseEntity.ok().body(Collections.singletonMap("mensaje", "Usuario eliminado correctamente."));
            } else {
                return ResponseEntity.ok().body(Collections.singletonMap("mensaje", "El usuario no existe."));
            }
        } catch (Exception ex) {
            Logger.getLogger(SubastaController.class.getName()).log(Level.SEVERE, "NO SE PUDO ELIMINAR");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("mensaje", "No se pudo eliminar."));
        }
    }

    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo" + err.getField()
                    + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
