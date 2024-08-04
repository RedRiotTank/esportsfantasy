package htt.esportsfantasybe.model.pojos;

import htt.esportsfantasybe.DTO.PlayerDTO;
import htt.esportsfantasybe.DTO.TeamDTO;
import htt.esportsfantasybe.DTO.UserDTO;
import htt.esportsfantasybe.model.complexentities.Market;
import htt.esportsfantasybe.model.complexentities.PlayerPoints;
import lombok.Getter;
import lombok.Setter;
import org.yaml.snakeyaml.error.Mark;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class PlayerInfoPOJO {
    private UUID uuid;
    private String name;
    private String fullName;
    private String role;
    private String Icon;
    private List<UUID> teamsList;
    private UUID ownerUUID;
    private String ownerUsername;
    private String ownerIcon;
    private int price;
    private int points;
    List<PlayerPoints> playerPoints;
    private int clause;
    private ArrayList<Integer> pointsHistory;

    public PlayerInfoPOJO(UUID uuid, String name, String role, Set<TeamDTO> teamsDTO, UUID ownerUUID, String ownerUsername, int price, int points, int clause, ArrayList<Integer> pointsHistory) {
        this.uuid = uuid;
        this.name = name;
        this.role = role;
        this.teamsList = teamsDTO.stream().map(TeamDTO::getUuid).toList();
        this.ownerUUID = ownerUUID;
        this.ownerUsername = ownerUsername;
        this.price = price;
        this.points = points;
        this.clause = clause;
        this.pointsHistory = pointsHistory;
    }


    public PlayerInfoPOJO(
        UUID uuid,
        String name,
        String fullName,
        String role,
        String Icon,
        List<UUID> teamsList,
        int price,
        List<PlayerPoints> playerPoints,

        UUID ownerUUID,
        String ownerUsername,
        String ownerIcon,
        int clause
    ) {
        //player
        this.uuid = uuid;
        this.name = name;
        this.fullName = fullName;
        this.role = role;
        this.Icon = Icon;
        this.teamsList = teamsList;
        this.price = price;
        this.playerPoints = playerPoints;

        //owner
        this.ownerUUID = ownerUUID;
        this.ownerUsername = ownerUsername;
        this.ownerIcon = ownerIcon;
        this.clause = clause;



    }




}
