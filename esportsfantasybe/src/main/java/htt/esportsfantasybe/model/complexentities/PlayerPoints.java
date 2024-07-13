package htt.esportsfantasybe.model.complexentities;

import htt.esportsfantasybe.model.complexkeysmodels.PlayerPointsId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "playerpoints")
public class PlayerPoints {

    @EmbeddedId
    private PlayerPointsId id;

    int points;

    public PlayerPoints(String matchid, UUID playeruuid, int points) {
        this.id = new PlayerPointsId(matchid, playeruuid);
        this.points = points;
    }

    public PlayerPoints() {
    }
}
