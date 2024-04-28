package htt.esportsfantasybe.model.pojos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinLeaguePOJO {
    private int leagueType;
    private String gameType;
    private String competition;
    private String leagueName;
    private boolean clauseActive;
    private int startType;
    private String code;
    private String userMail;

}
