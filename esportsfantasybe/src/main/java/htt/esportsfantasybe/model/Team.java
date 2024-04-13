package htt.esportsfantasybe.model;

import htt.esportsfantasybe.DTO.TeamDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "teamxrleague",
            joinColumns = @JoinColumn(name = "teamuuid"),
            inverseJoinColumns = @JoinColumn(name = "leagueuuid"))
    private Set<RealLeague> leagues;


    @ManyToMany(mappedBy = "teams",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<Player> players;


    public Team(TeamDTO teamDTO) {
        this.name = teamDTO.getName();
        this.image = teamDTO.getImage();
        this.shortname = teamDTO.getShortName();
        this.overviewpage = teamDTO.getOverviewPage();
        this.game = teamDTO.getGame();

        if (teamDTO.getPlayers() != null)
            this.players = teamDTO.getPlayers().stream().map(Player::new).collect(Collectors.toSet());
        else
            this.players = null;
    }

    public Team() {

    }
}
