package htt.esportsfantasybe.model.pojos;

import htt.esportsfantasybe.DTO.PlayerDTO;
import htt.esportsfantasybe.DTO.TeamDTO;
import htt.esportsfantasybe.DTO.UserDTO;
import htt.esportsfantasybe.model.complexentities.Market;
import lombok.Getter;
import lombok.Setter;
import org.yaml.snakeyaml.error.Mark;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class PlayerInfoPOJO {
    private UUID uuid;
    private String name;
    private String role;
    private List<UUID> teamsList;
    private UUID ownerUUID;
    private String ownerUsername;
    private int price;
    private int points;
    private int clause;

    public PlayerInfoPOJO(UUID uuid, String name, String role, Set<TeamDTO> teamsDTO, UUID ownerUUID, String ownerUsername, int price, int points, int clause) {
        this.uuid = uuid;
        this.name = name;
        this.role = role;
        this.teamsList = teamsDTO.stream().map(TeamDTO::getUuid).toList();
        this.ownerUUID = ownerUUID;
        this.ownerUsername = ownerUsername;
        this.price = price;
        this.points = points;
        this.clause = clause;
    }







}
