//package com.examcomplexivo.subastainversaservices.utils;
//
//import com.examcomplexivo.subastainversaservices.security.entity.Rol;
//import com.examcomplexivo.subastainversaservices.security.enums.RolNombre;
//import com.examcomplexivo.subastainversaservices.security.service.rol.RolServiceImp;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//// * Esta clase nos crea los roles por defecto
//@Component
//public class CreateRoles implements CommandLineRunner {
//
//    @Autowired
//    RolServiceImp rolServiceImp;
//
//    @Override
//    public void run(String... args) throws Exception {
//        Rol rolAdmin = new Rol(RolNombre.ROLE_ADMIN);
//        Rol rolCliente = new Rol(RolNombre.ROLE_CLIENTE);
//        Rol rolProveedor = new Rol(RolNombre.ROLE_PROVEEDOR);
//
//        rolServiceImp.guardar(rolAdmin);
//        rolServiceImp.guardar(rolCliente);
//        rolServiceImp.guardar(rolProveedor);
//    }
//}
