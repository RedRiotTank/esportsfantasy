package htt.esportsfantasybe.DTO;

import htt.esportsfantasybe.model.pojos.UserOffer;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class PlayerOffersDTO {
    private UUID playerUuid;
    private String playerUsername;
    private String playerIcon;
    private String playerRole;
    private int value;
    private List<Integer> points;
    private List<UserOffer> offers;

    public PlayerOffersDTO(UUID playerUuid, String playerUsername, String playerIcon, String playerRole, int value, List<Integer> points) {
        this.playerUuid = playerUuid;
        this.playerUsername = playerUsername;
        this.playerIcon = playerIcon;
        this.playerRole = playerRole;
        this.value = value;
        this.points = points;
        this.offers = new ArrayList<>();
    }
}
