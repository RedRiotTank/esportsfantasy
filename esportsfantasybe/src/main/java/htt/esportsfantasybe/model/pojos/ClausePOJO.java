package htt.esportsfantasybe.model.pojos;

import lombok.Data;

import java.util.UUID;

@Data
public class ClausePOJO {
    private UUID playeruuid;
    private UUID leagueuuid;
    private UUID useruuid;
    private UUID biduseruuid;
    private int jour;
    private int value;

    public ClausePOJO(UUID playeruuid, UUID leagueuuid, UUID useruuid, UUID biduseruuid, int jour, int value) {
        this.playeruuid = playeruuid;
        this.leagueuuid = leagueuuid;
        this.useruuid = useruuid;
        this.biduseruuid = biduseruuid;
        this.jour = jour;
        this.value = value;
    }
}
