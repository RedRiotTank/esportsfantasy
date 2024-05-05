package htt.esportsfantasybe.model.pojos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLeagueInfoPOJO {
    private String leagueUUID;
    private String rLeagueUUID;
    private String leagueName;
    private Boolean isAdmin;
    private int money;

    public UserLeagueInfoPOJO(String leagueUUID, String leagueName, Boolean isAdmin, int money, String rLeagueUUID) {
        this.leagueUUID = leagueUUID;
        this.leagueName = leagueName;
        this.isAdmin = isAdmin;
        this.money = money;
        this.rLeagueUUID = rLeagueUUID;
    }
}
