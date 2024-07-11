package htt.esportsfantasybe.model.complexentities;

import htt.esportsfantasybe.model.complexkeysmodels.UserXLeagueXPlayerId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "userxleaguexplayer")
public class UserXLeagueXPlayer {

    @EmbeddedId
    private UserXLeagueXPlayerId id;

    private int aligned;

    public UserXLeagueXPlayer(UserXLeagueXPlayerId id, int aligned) {
        this.id = id;
        this.aligned = aligned;
    }

    public UserXLeagueXPlayer(UUID playeruuid, UUID leagueuuid, UUID useruuid, int jour, int aligned){
        this.id = new UserXLeagueXPlayerId(playeruuid,leagueuuid,useruuid,jour);
        this.aligned = aligned;
    }


    public UserXLeagueXPlayer() {
    }

}
