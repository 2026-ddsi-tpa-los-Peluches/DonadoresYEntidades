package ar.edu.utn.dds.k3003.events.donadorEvents;

import lombok.Getter;

import java.time.Instant;

// Mismo formato que consume ServicioConsultas en la cola consultas.insignia-donador-quitada.
@Getter
public class DonadorInsigniaQuitadaEvent {
    private String donadorId;
    private String insigniaId;
    private Instant fechaEvento;

    public DonadorInsigniaQuitadaEvent(String donadorId, String insigniaId) {
        this.donadorId = donadorId;
        this.insigniaId = insigniaId;
        this.fechaEvento = Instant.now();
    }
}
