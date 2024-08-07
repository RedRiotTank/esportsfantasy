package htt.esportsfantasybe.model.pojos;

import lombok.Data;

import java.util.UUID;

@Data
public class IncreaseClausePOJO {
    private UUID playerUuid;
    private UUID userUuid;
    private UUID leagueUuid;
    private int increaseType;

    public IncreaseClausePOJO(UUID playerUuid, UUID  userUuid,UUID leagueUuid, int increaseType) {
        this.playerUuid = playerUuid;
        this.userUuid = userUuid;
        this.leagueUuid = leagueUuid;
        this.increaseType = increaseType;
    }

}
