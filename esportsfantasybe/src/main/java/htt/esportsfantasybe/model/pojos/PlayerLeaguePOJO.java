package htt.esportsfantasybe.model.pojos;

import lombok.Getter;

@Getter
public class PlayerLeaguePOJO {
    String playeruuid;
    String leagueuuid;

    public PlayerLeaguePOJO( String playeruuid, String leagueuuid) {
        this.playeruuid = playeruuid;
        this.leagueuuid = leagueuuid;
    }
}
