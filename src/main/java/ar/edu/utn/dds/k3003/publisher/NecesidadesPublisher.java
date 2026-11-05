package ar.edu.utn.dds.k3003.publisher;

import ar.edu.utn.dds.k3003.events.CantidadRecibidaActualizadaEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class NecesidadesPublisher {

    private final EventPublisher eventPublisher;

    // Inyección por constructor (siguiendo tu estándar)
    public NecesidadesPublisher(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void notificarCantidadRecibida(Integer necesidadId, Integer cantidad) {
        CantidadRecibidaActualizadaEvent evento = new CantidadRecibidaActualizadaEvent(
                String.valueOf(necesidadId),
                cantidad
        );

        eventPublisher.publicar("necesidad-cantidad-recibida", evento);
        System.out.println("Evento de necesidad cantidad recibida enviado a RabbitMQ para la necesidad ID: " + necesidadId);
    }
}
