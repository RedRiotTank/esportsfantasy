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
    private boolean activeClause;
    private int startingType;

    public LeagueDTO(UUID uuid, String name, boolean activeClause, int startingType) {
        this.uuid = uuid;
        this.name = name;
        this.activeClause = activeClause;
        this.startingType = startingType;

    }

    public LeagueDTO(League league) {
        this(league.getUuid(), league.getName(), league.isActiveclause(), league.getStartingtype());
    }
}
