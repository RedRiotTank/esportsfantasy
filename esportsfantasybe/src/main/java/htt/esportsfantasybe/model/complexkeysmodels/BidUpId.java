package htt.esportsfantasybe.model.complexkeysmodels;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Embeddable
public class BidUpId implements Serializable {
    private UUID playeruuid;
    private UUID leagueuuid;
    private UUID biduseruuid;
    private Date date;

    public BidUpId(UUID playeruuid, UUID leagueuuid, UUID biduseruuid, Date date) {
        this.playeruuid = playeruuid;
        this.leagueuuid = leagueuuid;
        this.biduseruuid = biduseruuid;
        this.date = date;
    }

    public BidUpId() {

    }
}
