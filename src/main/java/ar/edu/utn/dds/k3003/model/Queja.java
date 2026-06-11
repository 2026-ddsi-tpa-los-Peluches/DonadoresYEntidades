package ar.edu.utn.dds.k3003.model;

import ar.edu.utn.dds.k3003.catedra.dtos.donadoresYEntidades.EstadoDonadorEnum;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

// Importaciones correctas de Jakarta (Spring Boot 3+)
import jakarta.persistence.*;

import java.time.LocalDate;


@Setter
@Getter
@Entity
@Table(name = "quejas")
public class Queja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "donacion_id")
    private String donacionId;

    @Column(name = "donador_id")
    private String donadorId;

    @Column(name = "motivo")
    private String motivo;

    @Column(name = "fecha")
    private LocalDate fecha;

    public Queja() {
    }

    public Queja(
            Integer id,
            String donacionId,
            String donadorId,
            String motivo,
            LocalDate fecha)
    {
        this.id = id;
        this.donacionId = donacionId;
        this.donadorId = donadorId;
        this.motivo = motivo;
        this.fecha = fecha;
    }

}
