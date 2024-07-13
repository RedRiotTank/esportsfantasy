package htt.esportsfantasybe.model.complexkeysmodels;

import htt.esportsfantasybe.model.Player;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Embeddable
public class PlayerPointsId implements Serializable {
    private String matchid;
    private UUID playeruuid;

    public PlayerPointsId(String matchid, UUID playeruuid) {
        this.matchid = matchid;
        this.playeruuid = playeruuid;
    }

    public PlayerPointsId() {
    }
}
