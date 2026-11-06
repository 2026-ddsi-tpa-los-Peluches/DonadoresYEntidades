package ar.edu.utn.dds.k3003.publisher;


import ar.edu.utn.dds.k3003.events.donadorEvents.*;
import ar.edu.utn.dds.k3003.model.Donador;
import ar.edu.utn.dds.k3003.model.Queja;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;

@Component
public class DonadoresPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final EventPublisher eventPublisher;

    // Inyección por constructor (siguiendo tu estándar)
    public DonadoresPublisher(RabbitTemplate rabbitTemplate, EventPublisher eventPublisher) {
        this.rabbitTemplate = rabbitTemplate;
        this.eventPublisher = eventPublisher;
    }

    public void notificarCambioCategoria(Integer donadorId, String nuevaCategoria) {

        // Creamos el evento con los datos necesarios y el timestamp actual
        DonadorCategoriaActualizadaEvent evento = new DonadorCategoriaActualizadaEvent(
                String.valueOf(donadorId),
                nuevaCategoria,
                LocalDateTime.now()
        );

        // Publicamos utilizando tu eventPublisher hacia el exchange / routing key correspondiente
        eventPublisher.publicar("donador-categoria", evento); // Reemplazá por tu exchange/routing key

        System.out.println("Categoría actualizada enviada a RabbitMQ para el donador ID: " + donadorId);
    }

    public void notificarQuejaRegistrada(Queja queja){
        QuejaDonadorRegistradaEvent evento = new QuejaDonadorRegistradaEvent(
                queja.getId(),
                queja.getDonacionId(),
                queja.getDonadorId(),
                queja.getMotivo(),
                queja.getFecha()
        );

        eventPublisher.publicar("donador-queja-registrada", evento);

        System.out.println("Queja enviada a RabbitMQ para el donador ID: " + queja.getDonacionId());


    }

    public void notificarDonadorCreado(Donador donador) {
        DonadorCreadoEvent evento = new DonadorCreadoEvent(
                donador.getId(),
                donador.getNombre(),
                donador.getApellido(),
                donador.getEdad(),
                donador.getEmail(),
                donador.getNroDocumento(),
                donador.getDomicilio(),
                donador.getEstado() != null ? donador.getEstado().name() : null,
                donador.getCategoria()
        );

        eventPublisher.publicar("donador-creado", evento);
        System.out.println(" [x] Donador creado enviado a RabbitMQ con ID: " + donador.getId());
    }

    public void notificarEstadoDonador(Donador donador) {
        DonadorEstadoActualizadoEvent evento = new DonadorEstadoActualizadoEvent(
                donador.getId().toString(),
                donador.getEstado(),
                Instant.now()
        );

        eventPublisher.publicar("donador-estado", evento);
        System.out.println(" [x] Donador creado enviado a RabbitMQ con ID: " + donador.getId());
    }

    public void notificarSetMisionDonador(Donador donador) {
        DonadorMisionEvent evento = new DonadorMisionEvent(
                donador.getId().toString(),
                donador.getMisionActualID()
        );

        eventPublisher.publicar("donador-set-mision", evento);
        System.out.println(" [x] Set Mision Donador Id : " + donador.getId());
    }
}