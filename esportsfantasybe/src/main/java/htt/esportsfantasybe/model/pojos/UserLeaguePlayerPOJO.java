package htt.esportsfantasybe.model.pojos;

import lombok.Getter;

import java.util.UUID;

@Getter
public class UserLeaguePlayerPOJO {

    private UUID useruuid;
    private UUID leagueuuid;
    private UUID playeruuid;
    private int aligned;

    public UserLeaguePlayerPOJO(UUID useruuid, UUID leagueuuid, UUID playeruuid, int aligned) {
        this.useruuid = useruuid;
        this.leagueuuid = leagueuuid;
        this.playeruuid = playeruuid;
        this.aligned = aligned;
    }
}
