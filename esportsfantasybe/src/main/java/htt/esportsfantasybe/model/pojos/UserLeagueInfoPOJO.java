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
    private String leagueGame;

    public UserLeagueInfoPOJO(String leagueUUID, String leagueName, Boolean isAdmin, int money, String rLeagueUUID, String leagueGame) {
        this.leagueUUID = leagueUUID;
        this.leagueName = leagueName;
        this.isAdmin = isAdmin;
        this.money = money;
        this.rLeagueUUID = rLeagueUUID;
        this.leagueGame = leagueGame;
    }
}
