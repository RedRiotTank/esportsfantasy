package htt.esportsfantasybe.model.pojos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLeagueInfoPOJO {
    private String leagueUUID;
    private String leagueName;
    private Boolean isAdmin;

    public UserLeagueInfoPOJO(String leagueUUID, String leagueName, Boolean isAdmin) {
        this.leagueUUID = leagueUUID;
        this.leagueName = leagueName;
        this.isAdmin = isAdmin;
    }
}
