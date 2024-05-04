package htt.esportsfantasybe.model.complexkeysmodels;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Embeddable
public class MarketId implements Serializable {
    private UUID playeruuid;
    private UUID leagueuuid;

    public MarketId(UUID playeruuid, UUID leagueuuid) {
        this.playeruuid = playeruuid;
        this.leagueuuid = leagueuuid;
    }

    public MarketId() {

    }
}
