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
    private String team1Score, team2Score;

    public EventDTO(UUID realleagueuuid, UUID team1uuid, UUID team2uuid, int jour, Date date, String team1Score, String team2Score) {
        this.id = new EventId(realleagueuuid, team1uuid, team2uuid, jour);
        this.date = date;
        this.team1Score = team1Score;
        this.team2Score = team2Score;
    }

    public EventDTO(Event event){
        this.id = event.getId();
        this.date = event.getDate();
        this.team1Score = event.getTeam1Score();
        this.team2Score = event.getTeam2Score();
    }

    public EventDTO() {

    }


}
