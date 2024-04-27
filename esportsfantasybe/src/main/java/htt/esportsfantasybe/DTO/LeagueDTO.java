package htt.esportsfantasybe.DTO;

import htt.esportsfantasybe.model.League;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class LeagueDTO {
    private UUID uuid;
    private String name;

    public LeagueDTO(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public LeagueDTO(League league) {
        this(league.getUuid(), league.getName());
    }
}
