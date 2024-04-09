package htt.esportsfantasybe.DTO;

import htt.esportsfantasybe.model.RealLeague;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class RealLeagueDTO {

    private UUID uuid;
    private String event;
    private String overviewpage;
    private String game;
    private String apiId;
    private String shortname;


    public RealLeagueDTO(UUID uuid, String event, String overviewPage, String shortname,String game){
        this.uuid = uuid;
        this.event = event;
        this.overviewpage = overviewPage;
        this.game = game;
        this.apiId = null;
        this.shortname = shortname;
    }

    public RealLeagueDTO(UUID uuid, String event, String overviewPage, String game,String shortname, String apiId){
        this.uuid = uuid;
        this.event = event;
        this.overviewpage = overviewPage;
        this.game = game;
        this.apiId = apiId;
        this.shortname = shortname;
    }

    public RealLeagueDTO(RealLeague realLeague){
        this.uuid = realLeague.getUuid();
        this.event = realLeague.getEvent();
        this.overviewpage = realLeague.getOverviewpage();
        this.game = realLeague.getGame();
        this.apiId = realLeague.getApiID();

    }
}
