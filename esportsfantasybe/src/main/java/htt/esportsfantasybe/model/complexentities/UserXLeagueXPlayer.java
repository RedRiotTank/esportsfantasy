package htt.esportsfantasybe.model.complexentities;

import htt.esportsfantasybe.model.complexkeysmodels.UserXLeagueXPlayerId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "userxleaguexplayer")
public class UserXLeagueXPlayer {

    @EmbeddedId
    private UserXLeagueXPlayerId id;

    private boolean aligned;

    public UserXLeagueXPlayer(UserXLeagueXPlayerId id, boolean aligned) {
        this.id = id;
        this.aligned = aligned;
    }

    public UserXLeagueXPlayer() {
    }

}
