package ar.edu.utn.dds.k3003.events;

import lombok.Getter;


@Getter
public class DonadorMisionEvent {
    private String donadorId;
    private String misionActualId;

    public DonadorMisionEvent(String donadorId, String misionActualId) {
        this.donadorId = donadorId;
        this.misionActualId = misionActualId;
    }
}
