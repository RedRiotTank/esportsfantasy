package htt.esportsfantasybe.model.pojos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserLeaguePOJO {
    private UUID useruuid;
    private UUID leagueuuid;

    public UserLeaguePOJO(UUID useruuid, UUID leagueuuid) {
        this.useruuid = useruuid;
        this.leagueuuid = leagueuuid;
    }


}
