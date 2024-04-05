package htt.esportsfantasybe.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    private String game;

    public RealLeague() {

    }

    public RealLeague( String event, String overviewpage, String game) {
        this.event = event;
        this.overviewpage = overviewpage;
        this.game = game;
    }


}
