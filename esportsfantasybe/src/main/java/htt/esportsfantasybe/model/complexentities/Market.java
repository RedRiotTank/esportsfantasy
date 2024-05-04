package htt.esportsfantasybe.model.complexentities;

import htt.esportsfantasybe.model.League;
import htt.esportsfantasybe.model.Team;
import htt.esportsfantasybe.model.complexkeysmodels.MarketId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "market")
public class Market {

    @EmbeddedId
    private MarketId id;

    private UUID owneruuid;
    private int clause;
    private boolean insell;
    private int maxbid;
    private UUID biduseruuid;


    public Market(MarketId id, UUID owneruuid, int clause, boolean insell, int maxbid, UUID biduseruuid) {
        this.id = id;
        this.owneruuid = owneruuid;
        this.clause = clause;
        this.insell = insell;
        this.maxbid = maxbid;
        this.biduseruuid = biduseruuid;
    }

    public Market() {

    }
}
