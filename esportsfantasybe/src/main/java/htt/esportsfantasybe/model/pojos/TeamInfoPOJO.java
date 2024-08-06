package htt.esportsfantasybe.model.pojos;

import htt.esportsfantasybe.model.Player;
import htt.esportsfantasybe.model.RealLeague;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class TeamInfoPOJO {

    //Team specific info
    private UUID uuid;
    private String name;
    private String shortName;
    private String game;
    private String icon;

    //league team plays in info:
    private Set<RealLeague> teamRLeagues;


    //Team players info
    private Set<Player> players;

    public TeamInfoPOJO(
            UUID uuid,
            String name,
            String shortName,
            String game,
            String icon,
            Set<Player> players,
            Set<RealLeague> teamRLeagues
            ) {
        this.uuid = uuid;
        this.name = name;
        this.shortName = shortName;
        this.game = game;
        this.icon = icon;
        this.players = players;
        this.teamRLeagues = teamRLeagues;


    }

}
