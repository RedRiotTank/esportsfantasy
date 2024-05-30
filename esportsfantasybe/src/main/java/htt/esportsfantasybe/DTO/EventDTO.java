package htt.esportsfantasybe.DTO;

import htt.esportsfantasybe.model.complexentities.Event;
import htt.esportsfantasybe.model.complexkeysmodels.EventId;
import lombok.Getter;

import java.util.Date;
import java.util.UUID;

@Getter
public class EventDTO {

    private EventId id;
    private Date date;
    private UUID winner;

    public EventDTO(UUID realleagueuuid, UUID team1uuid, UUID team2uuid, int jour, Date date, UUID winner) {
        this.id = new EventId(realleagueuuid, team1uuid, team2uuid, jour);
        this.date = date;
        this.winner = winner;
    }

    public EventDTO(Event event){
        this.id = event.getId();
        this.date = event.getDate();
        this.winner = event.getWinner();
    }

    public EventDTO() {

    }


}
