package htt.esportsfantasybe.model;

import com.nimbusds.jose.util.JSONObjectUtils;
import htt.esportsfantasybe.DTO.PlayerDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "player")
public class Player {

    @Id
    @GeneratedValue(generator = "uuid2", strategy = GenerationType.AUTO)
    private UUID uuid;

    private String username;
    private String fullname;
    private String image;
    private String role;
    private float value;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "teamxplayer",
            joinColumns = @JoinColumn(name = "playeruuid"),
            inverseJoinColumns = @JoinColumn(name = "teamuuid"))
    private Set<Team> teams;

    public Player(UUID uuid, String username, String fullname, String role, float value) {
        this.uuid = uuid;
        this.username = username;
        this.fullname = fullname;
        this.role = role;
        this.value = value;
    }

    public Player(PlayerDTO playerDTO) {
        this(playerDTO.getUuid(), playerDTO.getUsername(), playerDTO.getFullname(), playerDTO.getRole(), playerDTO.getValue());
    }

    public Player() {

    }
    
}
