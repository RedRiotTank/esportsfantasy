package htt.esportsfantasybe.model;

import htt.esportsfantasybe.DTO.RealLeagueDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Setter
@Getter
@Entity
@Table(name = "realleague")
public class RealLeague {

    @Id
    @GeneratedValue(generator = "uuid2", strategy = GenerationType.AUTO)
    private UUID uuid;

    private String event;
    private String overviewpage;
    private String shortname;
    private String game;
    private String apiID;

    @ManyToMany(mappedBy = "leagues",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<Team> teams;

    public RealLeague() {

    }

    public RealLeague( String event, String overviewpage,String shortname, String game,String apiID, Set<Team> teams) {
        this.event = event;
        this.overviewpage = overviewpage;
        this.shortname = shortname;
        this.game = game;
        this.apiID = apiID;
        this.teams = teams;
    }


    public RealLeague(String event, String overviewpage, String shortname, String game) {
        this(event, overviewpage, shortname, game, null,null);
    }



    public RealLeague(RealLeagueDTO realLeagueDTO){
        this(realLeagueDTO.getEvent(), realLeagueDTO.getOverviewpage(), realLeagueDTO.getShortname(), realLeagueDTO.getGame(), realLeagueDTO.getApiId(), realLeagueDTO.getTeams().stream().map(Team::new).collect(Collectors.toSet()));
    }


}
