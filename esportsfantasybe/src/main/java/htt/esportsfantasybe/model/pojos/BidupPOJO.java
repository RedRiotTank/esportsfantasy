package htt.esportsfantasybe.model.pojos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class BidupPOJO {
    private UUID playeruuid;
    private UUID leagueuuid;
    private UUID useruuid;
    private int value;

    public BidupPOJO(String playeruuid, String leagueuuid, String useruuid, int value) {
        this.playeruuid = UUID.fromString(playeruuid);
        this.leagueuuid = UUID.fromString(leagueuuid);
        this.useruuid = UUID.fromString(useruuid);
        this.value = value;
    }

    public BidupPOJO() {

    }
}
