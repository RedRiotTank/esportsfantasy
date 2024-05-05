package htt.esportsfantasybe.DTO;

import htt.esportsfantasybe.model.RealLeague;
import htt.esportsfantasybe.model.Team;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
public class TeamDTO {

    private UUID uuid;
    private String name;
    private String OverviewPage;
    private String shortName;
    private String image;
    private String game;
    private Set<RealLeague> leagues;
    private Set<PlayerDTO> players;

    public TeamDTO(UUID uuid, String name, String overviewPage, String shortName, String image, String game, Set<PlayerDTO> players) {
        this.uuid = uuid;
        this.name = name;
        this.OverviewPage = overviewPage;
        this.shortName = shortName;
        this.image = image;
        this.game = game;
        this.players = players;
    }

    public TeamDTO( String name, String overviewPage, String shortName, String image, String game, Set<PlayerDTO> players) {
        this(null,name,overviewPage,shortName,image,game,players);
    }

    public TeamDTO(Team team) {
        this.uuid = team.getUuid();
        this.name = team.getName();
        this.image = team.getImage();
        this.OverviewPage = team.getOverviewpage();
        this.shortName = team.getShortname();
        this.game = team.getGame();
        this.leagues =team.getLeagues();

    }

    public TeamDTO(Team team, boolean copyPlayers) {
        this.uuid = team.getUuid();
        this.name = team.getName();
        if (copyPlayers) {
            this.players = team.getPlayers().stream()
                    .map(PlayerDTO::new)
                    .collect(Collectors.toSet());
        }
    }


    public void printTeam(){
        System.out.println("============ " + this.name + " ============");
        System.out.println("OverviewPage: " + this.OverviewPage);
        System.out.println("ShortName: " + this.shortName);
        System.out.println("Image: " + this.image);
        System.out.println("Game: " + this.game);
    }
}
