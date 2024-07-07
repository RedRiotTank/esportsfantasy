package htt.esportsfantasybe.model.complexentities;

import htt.esportsfantasybe.DTO.EventDTO;
import htt.esportsfantasybe.model.complexkeysmodels.EventId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "event")
public class Event {

    @EmbeddedId
    private EventId id;
    private Date date;
    private String team1Score, team2Score;

    public Event(UUID realleagueuuid, UUID team1uuid, UUID team2uuid, int jour, Date date, String team1Score, String team2Score) {
        this.id = new EventId(realleagueuuid, team1uuid, team2uuid, jour);
        this.date = date;
        this.team1Score = team1Score;
        this.team2Score = team2Score;
    }

    public Event(EventDTO eventDTO) {
        this.id = new EventId(eventDTO.getId().getRealleagueuuid(), eventDTO.getId().getTeam1uuid(), eventDTO.getId().getTeam2uuid(), eventDTO.getId().getJour());
        this.date = eventDTO.getDate();
        this.team1Score = eventDTO.getTeam1Score();
        this.team2Score = eventDTO.getTeam2Score();
    }

    public Event() {

    }
}
