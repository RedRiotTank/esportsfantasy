package htt.esportsfantasybe.model.complexentities;

import htt.esportsfantasybe.model.complexkeysmodels.BidUpId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "bidup")
public class BidUp {
    @EmbeddedId
    private BidUpId id;
    private int bid;
    private boolean state;

    public BidUp(UUID playeruuid, UUID leagueuuid, UUID biduseruuid, Date date, int bid, boolean state) {
        this.id = new BidUpId(playeruuid, leagueuuid, biduseruuid, date);
        this.bid = bid;
        this.state = state;
    }

    public BidUp() {

    }


    public UUID getPlayerUuid() {
        return id.getPlayeruuid();
    }
}
