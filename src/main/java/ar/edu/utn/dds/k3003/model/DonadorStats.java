package ar.edu.utn.dds.k3003.model;

import ar.edu.utn.dds.k3003.catedra.dtos.donadoresYEntidades.EstadoDonadorEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "donador_stats")
public class DonadorStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "edad")
    private Integer edad;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoDonadorEnum estado;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "mision_actual_id")
    private String misionActualID;

    // Crea una tabla auxiliar para guardar la lista de IDs de insignias (Strings)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "donador_stats_insignias", joinColumns = @JoinColumn(name = "donador_stats_id"))
    @Column(name = "insignia_id")
    private List<String> insigniasID = new ArrayList<>();

    // Constructor vacío OBLIGATORIO para JPA
    public DonadorStats() {
    }

    public DonadorStats(
            Integer id,
            String nombre,
            String apellido,
            Integer edad,
            String misionActualID) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.estado = EstadoDonadorEnum.VERIFICADO;
        this.categoria = "Ocasional";
        this.misionActualID = misionActualID;
    }

    public DonadorStats(
            Integer id,
            String nombre,
            String apellido,
            Integer edad,
            EstadoDonadorEnum estado,
            String categoria,
            String misionActualID) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.estado = estado;
        this.categoria = categoria;
        this.misionActualID = misionActualID;
    }
}
