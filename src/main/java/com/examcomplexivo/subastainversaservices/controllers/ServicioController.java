package com.examcomplexivo.subastainversaservices.controllers;

import com.examcomplexivo.subastainversaservices.models.Cliente;
import com.examcomplexivo.subastainversaservices.models.Servicio;
import com.examcomplexivo.subastainversaservices.services.servicio.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT,
        RequestMethod.DELETE})
@RequestMapping("/auth/servicio")
public class ServicioController {

    @Autowired
    private ServicioService service;

    @GetMapping("listar")
    public List<Servicio> listar() {
        return service.listar();
    }

    @GetMapping("listar/{filtro}")
    public List<Servicio> listarByFiltro(@PathVariable(name = "filtro", required = true) String filtro) {
        return service.findByFiltro(filtro);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("crear")
    public ResponseEntity<?> crear(@Valid @RequestBody Servicio servicio, BindingResult result) {

        if (result.hasErrors()) {
            return validar(result);
        }

        Optional<Servicio> existServicio = service.findByNombreServicio(servicio.getNombreServicio());
        if (existServicio.isPresent()) {
            return ResponseEntity.badRequest().body(
                    Collections.singletonMap("mensaje", "Este servicio ya se encuentra registrado.")
            );
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.guardar(servicio));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/editar/{idServicio}")
    public ResponseEntity<?> editarServicio(@PathVariable(name = "idServicio", required = true) Long idServicio,
                                            @Valid @RequestBody Servicio servicio, BindingResult result) {
        try {
            Optional<Servicio> servicioBD = service.findByNombreServicio(servicio.getNombreServicio());
            Optional<Servicio> existServicio = service.findById(idServicio);

            if (existServicio.isPresent()) {
                if (servicioBD.isPresent()){
                    if (!Objects.equals(servicioBD.get(), existServicio.get())){
                        return ResponseEntity.badRequest().body(
                                Collections.singletonMap("mensaje", "Este servicio ya se encuentra registrado.")
                        );
                    }

                    if (Objects.equals(servicioBD.get(), existServicio.get())
                            && servicio.getDescripcion_servicio().equalsIgnoreCase(existServicio.get().getDescripcion_servicio())){
                        return ResponseEntity.badRequest().body(
                                Collections.singletonMap("mensaje", "No se ha realizado ningun cambio.")
                        );
                    }
                }
                existServicio.get().setNombreServicio(servicio.getNombreServicio());
                existServicio.get().setDescripcion_servicio(servicio.getDescripcion_servicio());
                service.guardar(existServicio.get());
                return ResponseEntity.ok().body(Collections.singletonMap("mensaje", "Servicio modificado correctamente."));
            } else {
                return ResponseEntity.status(404).body(Collections.singletonMap("mensaje", "El servicio no existe."));
            }
        } catch (Exception ex) {
            Logger.getLogger(SubastaController.class.getName()).log(Level.SEVERE, "Imposible realizar cambios " + ex.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/eliminar/{idServicio}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> eliminarServicio(@PathVariable(name = "idServicio", required = true) Long idServicio) {
        try {
            if (service.findById(idServicio).isPresent()) {
                service.eliminar(idServicio);
                return ResponseEntity.ok().body(Collections.singletonMap("mensaje", "Servicio eliminado correctamente."));
            } else {
                return ResponseEntity.ok().body(Collections.singletonMap("mensaje", "El servicio no existe."));
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
