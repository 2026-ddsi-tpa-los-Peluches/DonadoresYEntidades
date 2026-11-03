package ar.edu.utn.dds.k3003.events;

import java.time.LocalDateTime;

public record DonadorCategoriaActualizadaEvent(
        String donadorId,
        String nuevaCategoria,
        LocalDateTime fechaCambio
) {}