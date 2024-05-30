package htt.esportsfantasybe.DTO;

import htt.esportsfantasybe.model.RealLeague;
import htt.esportsfantasybe.model.Team;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Setter
@Getter
public class RealLeagueDTO {

    private UUID uuid;
    private String event;
    private String overviewpage;
    private String shortname;
    private String game;
    private String apiId;
    private int currentjour;

    private Set<TeamDTO> teams;

    public RealLeagueDTO(UUID uuid, String event, String overviewPage,String shortname, String game, String apiId, Set<TeamDTO> teams, int currentjour){
        this.uuid = uuid;
        this.event = event;
        this.overviewpage = overviewPage;
        this.shortname = shortname;
        this.game = game;
        this.apiId = apiId;
        this.teams = teams;
        this.currentjour = currentjour;
    }

    public RealLeagueDTO(UUID uuid, String event, String overviewPage, String shortname,String game, int currentjour){
        this(uuid,event,overviewPage,shortname,game,null, null, currentjour);
    }

    public RealLeagueDTO(UUID uuid, String event, String overviewPage, String shortname,String game, Set<TeamDTO> teams, int currentjour){
        this(uuid,event,overviewPage,shortname,game,null, teams, currentjour);
    }

    public RealLeagueDTO(RealLeague realLeague){
        this(realLeague.getUuid(),
                realLeague.getEvent(),
                realLeague.getOverviewpage(),
                realLeague.getShortname(),
                realLeague.getGame(),
                realLeague.getApiID(),
                realLeague.getTeams().stream()
                        .map(team -> new TeamDTO(team, true))
                        .collect(Collectors.toSet()),
                realLeague.getCurrentjour()
        );
    }
}
