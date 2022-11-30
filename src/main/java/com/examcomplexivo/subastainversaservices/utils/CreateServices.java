//package com.examcomplexivo.subastainversaservices.utils;
//
//import com.examcomplexivo.subastainversaservices.models.Servicio;
//import com.examcomplexivo.subastainversaservices.services.servicio.ServicioService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//// * Esta clase nos crea los servicios por defecto
//@Component
//public class CreateServices implements CommandLineRunner {
//
//    @Autowired
//    private ServicioService service;
//
//    Servicio servicio1 = new Servicio("Plomero",
//            "Los Plomeros llevan a cabo el diagnóstico, instalación y reparación de tuberías utilizadas " +
//                    "para la distribución y desecho de aire, gas y agua en sistemas residenciales e industriales.");
//
//    Servicio servicio2 = new Servicio("Carpintero",
//            "Los Plomeros llevan a cabo el diagnóstico, instalación y reparación de tuberías utilizadas para la distribución y desecho de aire, gas y agua en sistemas residenciales e industriales.");
//
//    Servicio servicio3 = new Servicio("Gasfitero", "La carpintería es el oficio de trabajar la madera para crear objetos útiles y agradables al ser humano.");
//
//    Servicio servicio4 = new Servicio("Albañil", "Obrero que realiza todo tipo de trabajos básicos de construcción tales como: preparación de los materiales, alzado de paredes y muros.");
//
//    Servicio servicio5 = new Servicio("Cocinero",
//            "Un cocinero1 profesional es la persona que cocina por oficio y profesión, bien sea ejerciendo su trabajo particular o en una empresa que amerite o requiera la labor de un experto en la materia.");
//
//    Servicio servicio6 = new Servicio("Tutor privado", "Un tutor privado es una persona que trabaja individualmente con los estudiantes para proporcionar ayuda e instrucción adicional en un tema en particular.");
//
//    Servicio servicio7 = new Servicio("Asesor comercial", "Un asesor comercial es el principal responsable, dentro de una empresa, de proporcionar información detallada a potenciales clientes o a clientes ya fidelizados, para que se lleve a cabo la venta.");
//
//    @Override
//    public void run(String... args) throws Exception {
//        service.guardar(servicio1);
//        service.guardar(servicio2);
//        service.guardar(servicio3);
//        service.guardar(servicio4);
//        service.guardar(servicio5);
//        service.guardar(servicio6);
//        service.guardar(servicio7);
//    }
//}
