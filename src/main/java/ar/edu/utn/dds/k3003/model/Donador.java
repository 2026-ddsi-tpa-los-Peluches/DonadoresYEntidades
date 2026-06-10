package ar.edu.utn.dds.k3003.model;

import ar.edu.utn.dds.k3003.catedra.dtos.donadoresYEntidades.EstadoDonadorEnum;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

// Importaciones correctas de Jakarta (Spring Boot 3+)
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "donadores")
public class Donador {

  @Id // <--- Esta es la única anotación válida para la clave primaria
  private String id;

  @Column(name = "nombre")
  private String nombre;

  @Column(name = "apellido")
  private String apellido;

  @Column(name = "edad")
  private Integer edad;

  @Column(name = "email")
  private String email;

  @Column(name = "nro_documento")
  private String nroDocumento;

  @Column(name = "domicilio")
  private String domicilio;

  @Enumerated(EnumType.STRING)
  private EstadoDonadorEnum estado;

  @Column(name = "categoria")
  private String categoria;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "donador_historial", joinColumns = @JoinColumn(name = "donador_id"))
  @Enumerated(EnumType.STRING)
  @Column(name = "estado_historico")
  private List<EstadoDonadorEnum> historial = new ArrayList<>();

  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  @Column(name = "cantidad_quejas")
  private Integer cantidadQuejas = 0;

  // JPA EXIGE tener un constructor vacío
  public Donador() {
  }

  // Constructor con parámetros
  public Donador(
          String id,
          String nombre,
          String apellido,
          Integer edad,
          String email,
          String nroDocumento,
          String domicilio) {
    this.id = id;
    this.nombre = nombre;
    this.apellido = apellido;
    this.edad = edad;
    this.email = email;
    this.nroDocumento = nroDocumento;
    this.domicilio = domicilio;
    this.estado = EstadoDonadorEnum.VERIFICADO;
    this.categoria = "Ocasional";

    this.agregarEstadoHistorial();
  }

  public boolean puedeDonar() {
    return switch (estado) {
      case VERIFICADO -> true;
      case SOSPECHOSO -> Math.random() < 0.5;
      case BANEADO -> false;
    };
  }

  private void agregarEstadoHistorial() {
    if(!historial.isEmpty() && historial.getLast()!= this.estado) {
      historial.add(estado);
    }
  }

  public void recalcularEstado() {
    cantidadQuejas++;
    if (cantidadQuejas >= 10) {
      this.estado = EstadoDonadorEnum.BANEADO;
      this.agregarEstadoHistorial();
    } else if (cantidadQuejas >=5 ) {
      this.estado = EstadoDonadorEnum.SOSPECHOSO;
      this.agregarEstadoHistorial();
    }
  }
}
