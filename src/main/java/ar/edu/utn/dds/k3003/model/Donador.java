package ar.edu.utn.dds.k3003.model;

import ar.edu.utn.dds.k3003.catedra.dtos.donadoresYEntidades.EstadoDonadorEnum;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class Donador {

  private String id;
  private String nombre;
  private String apellido;
  private Integer edad;
  private String email;
  private String nroDocumento;
  private String domicilio;
  private EstadoDonadorEnum estado;
  private String categoria;
  private List<EstadoDonadorEnum> historial = new ArrayList<>();
  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  private Integer cantidadQuejas = 0;
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
  }
  public boolean puedeDonar() {
      return switch (estado) {
          case VERIFICADO -> true;
          case SOSPECHOSO -> Math.random() < 0.5;
          case BANEADO -> false;
      };
  }

  private void agregarEstadoHistorial() {
    historial.add(estado);
  }
  public void recalcularEstado() {
    cantidadQuejas++;
    if (cantidadQuejas >= 10) {
      this.estado = EstadoDonadorEnum.BANEADO;
    } else if (cantidadQuejas >=5 ) {
      this.estado = EstadoDonadorEnum.SOSPECHOSO;
    } else {
      this.estado = EstadoDonadorEnum.VERIFICADO;
    }
    this.agregarEstadoHistorial();
  }

}
