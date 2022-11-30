package com.examcomplexivo.subastainversaservices.models;


//import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Data
@Entity
@XmlRootElement
@Table(name = "subasta")
public class Subasta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id_subasta")
    private long idSubasta;

    @Column(name = "fecha_inicio_subasta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;

    @Column(name = "fecha_fin_subasta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;

    @Column(name = "estado_subasta")
    private String estadoSubasta;

    @Column(name = "titulo_subasta")
    private String tituloSubasta;

    @Column(name = "hora_cierre_subasta")
    private String horaCierreSubasta;

    @Column(name = "desc_subasta")
    private String descripcionSubasta;

    @Column(name = "img_subasta")
    private String imgSubasta;


    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    @ManyToOne
    private Cliente cliente;

    @JoinColumn(name = "id_servicio", referencedColumnName = "id_servicio")
    @ManyToOne
    private Servicio servicio;

}
