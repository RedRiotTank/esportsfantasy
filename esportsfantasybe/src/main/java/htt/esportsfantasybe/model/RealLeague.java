package htt.esportsfantasybe.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.UUID;

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

    @ManyToMany(mappedBy = "leagues")
    private Set<Team> teams;

    public RealLeague() {

    }

    public RealLeague(String event, String overviewpage, String shortname, String game) {
        this(event, overviewpage, shortname, game, null);
    }

    public RealLeague( String event, String overviewpage,String shortname, String game,String apiID) {
        this.event = event;
        this.overviewpage = overviewpage;
        this.game = game;
        this.apiID = apiID;
        this.shortname = shortname;
    }


}
