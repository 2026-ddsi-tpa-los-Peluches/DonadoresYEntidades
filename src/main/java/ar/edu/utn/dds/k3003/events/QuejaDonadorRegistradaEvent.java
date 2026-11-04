package ar.edu.utn.dds.k3003.events;


import java.time.LocalDate;

public record QuejaDonadorRegistradaEvent(
        Integer quejaId,
        String donacionId,
        String donadorId,
        String motivo,
        LocalDate fecha
) {}