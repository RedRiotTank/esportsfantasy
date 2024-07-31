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
    private int jour;
    private int aligned;
    private String icon;
    private int value;

    public PlayerTeamInfoPOJO(UUID playeruuid, String username, String fullname, String role, int value,int jour, int aligned, String icon) {
        this.playeruuid = playeruuid;
        this.username = username;
        this.fullname = fullname;
        this.role = role;
        this.value = value;
        this.jour = jour;
        this.aligned = aligned;
        this.icon = icon;
    }

}
