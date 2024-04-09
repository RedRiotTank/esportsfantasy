package htt.esportsfantasybe.DTO;

import htt.esportsfantasybe.model.Team;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class TeamDTO {

    private UUID uuid;
    private String name;
    private String OverviewPage;
    private String shortName;
    private String image;
    private String game;

    public TeamDTO(String name, String overviewPage, String shortName, String image, String game) {
        this.name = name;
        this.OverviewPage = overviewPage;
        this.shortName = shortName;
        this.image = image;
        this.game = game;
    }

    public TeamDTO(Team team) {
        this.uuid = team.getUuid();
        this.name = team.getName();
        this.image = team.getImage();
        this.OverviewPage = team.getOverviewpage();
        this.shortName = team.getShortname();
        this.game = team.getGame();

    }


    public void printTeam(){
        System.out.println("============ " + this.name + " ============");
        System.out.println("OverviewPage: " + this.OverviewPage);
        System.out.println("ShortName: " + this.shortName);
        System.out.println("Image: " + this.image);
        System.out.println("Game: " + this.game);
    }
}
