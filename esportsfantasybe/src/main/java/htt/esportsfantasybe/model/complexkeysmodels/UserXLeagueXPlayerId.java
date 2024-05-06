package htt.esportsfantasybe.model.complexkeysmodels;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Embeddable
public class UserXLeagueXPlayerId implements Serializable {
    private UUID playeruuid;
    private UUID leagueuuid;
    private UUID useruuid;
    private int jour;

    public UserXLeagueXPlayerId(UUID playeruuid, UUID leagueuuid, UUID useruuid, int jour) {
        this.playeruuid = playeruuid;
        this.leagueuuid = leagueuuid;
        this.useruuid = useruuid;
        this.jour = jour;
    }

    public UserXLeagueXPlayerId() {

    }



}
