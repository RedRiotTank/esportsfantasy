package htt.esportsfantasybe.model;

import htt.esportsfantasybe.DTO.LeagueDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "league")
public class League {

    @Id
    @GeneratedValue(generator = "uuid2", strategy = GenerationType.AUTO)
    private UUID uuid;

    private String name;
    private boolean activeclause;
    private int startingtype;

    @ManyToMany(mappedBy = "leagues",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<User> users;

    public League(String name) {
        this.name = name;
    }

    public League(LeagueDTO leagueDTO) {
        this(leagueDTO.getName());
    }
    public League(String name, boolean activeclause, int startingtype) {
        this.name = name;
        this.activeclause = activeclause;
        this.startingtype = startingtype;

    }

    public League() {

    }
}
