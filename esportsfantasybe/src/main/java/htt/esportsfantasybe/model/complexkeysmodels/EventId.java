package htt.esportsfantasybe.model.complexkeysmodels;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Embeddable
public class EventId implements Serializable {
    private UUID realleagueuuid;
    private UUID team1uuid;
    private UUID team2uuid;
    private int jour;

    public EventId(UUID realleagueuuid, UUID team1uuid, UUID team2uuid, int jour) {
        this.realleagueuuid = realleagueuuid;
        this.team1uuid = team1uuid;
        this.team2uuid = team2uuid;
        this.jour = jour;
    }

    public EventId() {

    }
}
