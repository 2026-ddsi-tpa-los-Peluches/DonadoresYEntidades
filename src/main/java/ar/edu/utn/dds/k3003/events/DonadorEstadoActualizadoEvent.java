package ar.edu.utn.dds.k3003.events;

import ar.edu.utn.dds.k3003.catedra.dtos.donadoresYEntidades.EstadoDonadorEnum;
import lombok.Getter;

import java.time.Instant;


@Getter
public class DonadorEstadoActualizadoEvent {
    private String donadorId;
    private String estado;
    private Instant fechaEvento;


    public DonadorEstadoActualizadoEvent(String donadorId, EstadoDonadorEnum estado, Instant now) {
        this.donadorId = donadorId;
        this.estado  = estado.toString();
        this.fechaEvento = now;
    }
}
