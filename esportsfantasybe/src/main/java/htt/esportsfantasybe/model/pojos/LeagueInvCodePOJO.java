package htt.esportsfantasybe.model.pojos;

import lombok.Data;

import java.util.UUID;

@Data
public class LeagueInvCodePOJO {
    UUID leagueUuid;
    String code;

    public LeagueInvCodePOJO(UUID leagueUuid, String code) {
        this.leagueUuid = leagueUuid;
        this.code = code;
    }
}
