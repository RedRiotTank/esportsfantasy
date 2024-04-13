package htt.esportsfantasybe.model.complexkeysmodels;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Embeddable
public class TeamXPlayerId implements Serializable {
    private UUID teamuuid;
    private UUID playeruuid;

    public TeamXPlayerId(UUID teamuuid, UUID playeruuid) {
        this.teamuuid = teamuuid;
        this.playeruuid = playeruuid;
    }

    public TeamXPlayerId() {

    }

}
