package htt.esportsfantasybe.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RealLeagueDTO {
    private String event;
    private String overviewpage;
    private String game;


    public RealLeagueDTO(String event, String overviewPage, String game){
        this.event = event;
        this.overviewpage = overviewPage;
        this.game = game;
    }
}
