package htt.esportsfantasybe.model.complexkeysmodels;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Embeddable
public class UserXLeagueId implements Serializable {

    private UUID useruuid;
    private UUID leagueuuid;

    public UserXLeagueId(UUID useruuid, UUID leagueuuid) {
        this.useruuid = useruuid;
        this.leagueuuid = leagueuuid;
    }

    public UserXLeagueId() {

    }
}
