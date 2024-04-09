package htt.esportsfantasybe.model.complexentities;

import htt.esportsfantasybe.model.complexkeysmodels.TeamXLeagueId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "teamxrleague")
public class TeamXrLeague {

    @EmbeddedId
    private TeamXLeagueId id;

    public TeamXrLeague() {
    }

    public TeamXrLeague(UUID teamID, UUID leagueID) {
        this.id = new TeamXLeagueId(teamID, leagueID);
    }
}
