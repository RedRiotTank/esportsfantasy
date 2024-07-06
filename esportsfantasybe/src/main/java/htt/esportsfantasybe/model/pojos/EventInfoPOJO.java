package htt.esportsfantasybe.model.pojos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class EventInfoPOJO {
    private UUID team1uuid;
    private UUID team2uuid;
    private String team1name;
    private String team2name;
    private String team1icon;
    private String team2icon;
    private int jour;
    private Date date;

    public EventInfoPOJO(UUID team1uuid, UUID team2uuid, String team1name, String team2name, int jour, Date date) {
        this.team1uuid = team1uuid;
        this.team2uuid = team2uuid;
        this.team1name = team1name;
        this.team2name = team2name;
        this.jour = jour;
        this.date = date;
        team1icon = null;
        team2icon = null;
    }

    public EventInfoPOJO(UUID team1uuid, UUID team2uuid, String team1name, String team2name, String team1icon, String team2icon, int jour, Date date) {
        this(team1uuid, team2uuid, team1name, team2name, jour, date);
        this.team1icon = team1icon;
        this.team2icon = team2icon;
    }



    public EventInfoPOJO() {
    }
}
