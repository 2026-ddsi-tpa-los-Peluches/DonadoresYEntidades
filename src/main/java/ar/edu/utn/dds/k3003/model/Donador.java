package ar.edu.utn.dds.k3003.model;

import ar.edu.utn.dds.k3003.catedra.dtos.donadoresYEntidades.EstadoDonadorEnum;
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
  private List<EstadoDonadorEnum> historial;
  private List<Queja> quejas = new ArrayList<>();//deberia actualizar el dataMapper??

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

  public void registrarQueja(Queja queja) {
    this.quejas.add(queja);
    recalcularEstado();
  }
  private void agregarEstadoHistorial() {
    historial.add(estado);
  }
  private void recalcularEstado() {
    Integer cantidadQuejas = quejas.size();

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
