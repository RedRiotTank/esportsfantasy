package htt.esportsfantasybe.model.complexkeysmodels;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Embeddable
public class TeamXLeagueId implements Serializable {
    private UUID teamuuid;
    private UUID leagueuuid;

    public TeamXLeagueId(UUID teamuuid, UUID leagueuuid) {
        this.teamuuid = teamuuid;
        this.leagueuuid = leagueuuid;
    }

    public TeamXLeagueId() {

    }

}
