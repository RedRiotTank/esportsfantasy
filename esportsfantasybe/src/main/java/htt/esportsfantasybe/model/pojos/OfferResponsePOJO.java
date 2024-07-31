package htt.esportsfantasybe.model.pojos;


import lombok.Data;

import java.util.UUID;

@Data
public class OfferResponsePOJO {
    private UUID playeruuid;
    private UUID leagueuuid;
    private UUID useruuid;
    private UUID biduseruuid;
    private int jour;

    public OfferResponsePOJO(UUID playeruuid, UUID leagueuuid, UUID useruuid, UUID biduseruuid, int jour) {
        this.playeruuid = playeruuid;
        this.leagueuuid = leagueuuid;
        this.useruuid = useruuid;
        this.biduseruuid = biduseruuid;
        this.jour = jour;
    }

}
