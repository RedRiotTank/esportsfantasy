package htt.esportsfantasybe.model.pojos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PlayerTeamInfoPOJO {

    private UUID playeruuid;
    private String username;
    private String fullname;
    private String role;

    private int aligned;

    public PlayerTeamInfoPOJO(UUID playeruuid, String username, String fullname, String role, int aligned) {
        this.playeruuid = playeruuid;
        this.username = username;
        this.fullname = fullname;
        this.role = role;
        this.aligned = aligned;
    }

}
