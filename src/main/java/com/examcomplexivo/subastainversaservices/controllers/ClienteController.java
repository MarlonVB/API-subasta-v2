package com.examcomplexivo.subastainversaservices.controllers;

import com.examcomplexivo.subastainversaservices.models.Cliente;
import com.examcomplexivo.subastainversaservices.security.entity.Rol;
import com.examcomplexivo.subastainversaservices.security.entity.Usuario;
import com.examcomplexivo.subastainversaservices.security.enums.RolNombre;
import com.examcomplexivo.subastainversaservices.security.service.rol.RolService;
import com.examcomplexivo.subastainversaservices.security.service.usuario.UsuarioService;
import com.examcomplexivo.subastainversaservices.services.cliente.ClienteService;
import com.examcomplexivo.subastainversaservices.services.persona.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT,
        RequestMethod.DELETE })
@RequestMapping("/auth/cliente")
public class ClienteController {

    @Autowired
    private ClienteService service;
    @Autowired
    private UsuarioService serviceUser;
    @Autowired
    private PersonaService personaServiceImp;
    @Autowired
    private RolService rolService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("listar")
    public List<Cliente> listar() {
        return service.listar();
    }

    @GetMapping("listar/{filtro}")
    public List<Cliente> listarFiltro(@PathVariable(name = "filtro", required = true) String filtro) {
        return service.findByFiltros(filtro);
    }

    //@PreAuthorize("hasAnyRole('ADMIN','CLIENTE')")
    @PostMapping("crear")
    public ResponseEntity<?> crear(@Valid @RequestBody Cliente cliente, BindingResult result) {
        if (result.hasErrors()) {
            return validar(result);
        }
        Optional<Cliente> existClienteEmail = service.findByEmail(cliente.getEmail());
        Optional<Cliente> existClienteTelefono = service.findByTelefono(cliente.getTelefono());

        Optional<Usuario> existUsuarioNombre = serviceUser.findByNombreUsuario(cliente.getUsuario().getNombreUsuario());
        /**
         * Validacion para que los usuarios no se repitan
         * **/
        if (existUsuarioNombre.isPresent()) {
            return ResponseEntity.badRequest().body(
                    Collections.singletonMap("mensaje", "Este nombre de usuario ya esta en uso.")
            );
        }

        /**
         * Validacion para que los usuarios no se repitan
         * Solo se valida por email y telefono ya que los nombres y apellidos si se pueden repetir
         * **/
        if (existClienteEmail.isPresent() || personaServiceImp.findByEmail(cliente.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(
                    Collections.singletonMap("mensaje", "Ya hay un usuario registrado con este email.")
            );
        }
        if (existClienteTelefono.isPresent() || personaServiceImp.findByTelefono(cliente.getTelefono()).isPresent()) {
            return ResponseEntity.badRequest().body(
                    Collections.singletonMap("mensaje", "Ya existe un usuario registrado con este número de celular")
            );
        }

        cliente.getUsuario().setContraseniaUsuario(passwordEncoder.encode(cliente.getUsuario().getContraseniaUsuario()));
        Set<Rol> roles = new HashSet<>();
        roles.add(rolService.findByRolNombre(RolNombre.ROLE_CLIENTE).get());
        cliente.getUsuario().setRoles(roles);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.guardar(cliente));
    }

    @PutMapping("/editar/{idCliente}")
    @PreAuthorize("hasAnyRole('ADMIN','CLIENTE')")
    public ResponseEntity<?> editarCliente(@PathVariable(name = "idCliente", required = true) Long idCliente,
                                           @Valid @RequestBody Cliente cliente, BindingResult result) {
        if (result.hasErrors()) {
            return validar(result);
        }

        try {
            Optional<Cliente> clienteBD = service.findById(idCliente);
            Optional<Cliente> existClienteEmail = service.findByEmail(cliente.getEmail());
            Optional<Cliente> existClienteTelefono = service.findByTelefono(cliente.getTelefono());
            if (clienteBD.isPresent()) {

                /**
                 * Validacion para que no se repitan email de otro user al editar
                 * **/
                if (!existClienteEmail.get().equals(clienteBD.get())
                        && existClienteEmail.get().getEmail().equalsIgnoreCase(cliente.getEmail())) {
                    return ResponseEntity.badRequest().body(
                            Collections.singletonMap("mensaje", "Este email ya esta en uso.")
                    );
                }

                /**
                 * Validacion para que no se repitan telefono de otro user al editar
                 * **/
                if (!existClienteTelefono.get().equals(clienteBD.get())
                        && existClienteTelefono.get().getTelefono().equalsIgnoreCase(cliente.getTelefono())) {
                    return ResponseEntity.badRequest().body(
                            Collections.singletonMap("mensaje", "Este telefono ya esta en uso.")
                    );
                }

                clienteBD.get().setNombre(cliente.getNombre());
                clienteBD.get().setApellido(cliente.getApellido());
                clienteBD.get().setEmail(cliente.getEmail());
                clienteBD.get().setTelefono(cliente.getTelefono());
                clienteBD.get().setDireccion(cliente.getDireccion());

                service.guardar(clienteBD.get());
                return ResponseEntity.ok().body(Collections.singletonMap("mensaje", "Cliente modificado correctamente."));
            } else {
                return ResponseEntity.status(404).body(Collections.singletonMap("mensaje", "El cliente no existe."));
            }
        } catch (Exception ex) {
            Logger.getLogger(SubastaController.class.getName()).log(Level.SEVERE, "Imposible realizar cambios");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/eliminar/{idCliente}")
    @PreAuthorize("hasAnyRole('ADMIN','CLIENTE')")
    public ResponseEntity<?> eliminarCliente(@PathVariable(name = "idCliente", required = true) Long idCliente) {
        try {
            if (service.findById(idCliente).isPresent()) {
                service.eliminar(idCliente);
                return ResponseEntity.ok().body(Collections.singletonMap("mensaje", "Cliente eliminado correctamente."));
            } else {
                return ResponseEntity.ok().body(Collections.singletonMap("mensaje", "El cliente no existe."));
            }
        } catch (Exception ex) {
            Logger.getLogger(SubastaController.class.getName()).log(Level.SEVERE, "NO SE PUDO ELIMINAR");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("mensaje", "No se pudo eliminar."));
        }
    }

    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put("mensaje", "El campo " + err.getField()
                    + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
