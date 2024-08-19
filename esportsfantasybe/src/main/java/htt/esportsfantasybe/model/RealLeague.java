package htt.esportsfantasybe.model;

import htt.esportsfantasybe.DTO.PlayerDTO;
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
    private Integer currentjour = -1;
    private boolean isplayoff;

    @ManyToMany(mappedBy = "leagues",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<Team> teams;

    public RealLeague() {

    }

    public RealLeague( String event, String overviewpage,String shortname, String game,String apiID, Set<Team> teams, boolean isplayoff) {
        this.event = event;
        this.overviewpage = overviewpage;
        this.shortname = shortname;
        this.game = game;
        this.apiID = apiID;
        this.teams = teams;
        this.isplayoff = isplayoff;
    }


    public RealLeague(String event, String overviewpage, String shortname, String game, boolean isplayoff) {
        this(event, overviewpage, shortname, game, null,null, isplayoff);
    }



    public RealLeague(RealLeagueDTO realLeagueDTO){
        this.event = realLeagueDTO.getEvent();
        this.overviewpage = realLeagueDTO.getOverviewpage();
        this.shortname = realLeagueDTO.getShortname();
        this.game = realLeagueDTO.getGame();
        this.apiID = realLeagueDTO.getApiId();
        this.currentjour = realLeagueDTO.getCurrentjour();
        this.isplayoff = realLeagueDTO.isIsplayoff();

        if(realLeagueDTO.getTeams() != null)
            this.teams = realLeagueDTO.getTeams().stream().map(Team::new).collect(Collectors.toSet());
        else this.teams = null;

    }


}
