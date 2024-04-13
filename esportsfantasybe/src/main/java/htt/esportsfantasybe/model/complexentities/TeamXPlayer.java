package htt.esportsfantasybe.model.complexentities;

import htt.esportsfantasybe.model.complexkeysmodels.TeamXLeagueId;
import htt.esportsfantasybe.model.complexkeysmodels.TeamXPlayerId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "teamxplayer")
public class TeamXPlayer {

    @EmbeddedId
    private TeamXPlayerId id;

    public TeamXPlayer() {
    }

    public TeamXPlayer(UUID teamID, UUID playerID) {
        this.id = new TeamXPlayerId(teamID, playerID);
    }

}
