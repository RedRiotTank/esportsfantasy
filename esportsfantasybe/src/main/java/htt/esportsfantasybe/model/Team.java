package htt.esportsfantasybe.model;

import htt.esportsfantasybe.DTO.TeamDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue(generator = "uuid2", strategy = GenerationType.AUTO)
    private UUID uuid;

    private String name;
    private String image;
    private String shortname;
    private String overviewpage;
    private String game;

    @ManyToMany
    @JoinTable(name = "teamxrleague",
            joinColumns = @JoinColumn(name = "teamuuid"),
            inverseJoinColumns = @JoinColumn(name = "leagueuuid"))
    private Set<RealLeague> leagues;

    public Team(TeamDTO teamDTO) {
        this.name = teamDTO.getName();
        this.image = teamDTO.getImage();
        this.shortname = teamDTO.getShortName();
        this.overviewpage = teamDTO.getOverviewPage();
        this.game = teamDTO.getGame();
    }

    public Team() {

    }
}
