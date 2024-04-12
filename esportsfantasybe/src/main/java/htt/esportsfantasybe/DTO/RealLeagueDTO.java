package htt.esportsfantasybe.DTO;

import htt.esportsfantasybe.model.RealLeague;
import htt.esportsfantasybe.model.Team;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Setter
@Getter
public class RealLeagueDTO {

    private UUID uuid;
    private String event;
    private String overviewpage;
    private String shortname;
    private String game;
    private String apiId;

    private Set<TeamDTO> teams;

    public RealLeagueDTO(UUID uuid, String event, String overviewPage,String shortname, String game, String apiId, Set<TeamDTO> teams){
        this.uuid = uuid;
        this.event = event;
        this.overviewpage = overviewPage;
        this.shortname = shortname;
        this.game = game;
        this.apiId = apiId;
        this.teams = teams;
    }

    public RealLeagueDTO(UUID uuid, String event, String overviewPage, String shortname,String game){
        this(uuid,event,overviewPage,shortname,game,null, null);
    }

    public RealLeagueDTO(UUID uuid, String event, String overviewPage, String shortname,String game, Set<TeamDTO> teams){
        this(uuid,event,overviewPage,shortname,game,null, teams);
    }

    public RealLeagueDTO(RealLeague realLeague){
        this(realLeague.getUuid(),
                realLeague.getEvent(),
                realLeague.getOverviewpage(),
                realLeague.getShortname(),
                realLeague.getGame(),
                realLeague.getApiID(),
                realLeague.getTeams().stream().map(TeamDTO::new).collect(java.util.stream.Collectors.toSet())
        );
    }
}
