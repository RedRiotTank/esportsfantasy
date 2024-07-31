package htt.esportsfantasybe.model.pojos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class UserOffer {

    //user
    private UUID userUuid;
    private String userName;
    private String userIcon;

    //player
    private UUID playerUuid;
    private String playerUsername;
    private String playerIcon;
    private String playerRole;
    private int value;
    private List<Integer> points;


    //offer
    private int offerValue;

    public UserOffer(UUID userUuid, String userName, String userIcon, UUID playerUuid, String playerUsername, String playerIcon, String playerRole, int value, List<Integer> points, int offerValue) {
        this.userUuid = userUuid;
        this.userName = userName;
        this.userIcon = userIcon;
        this.playerUuid = playerUuid;
        this.playerUsername = playerUsername;
        this.playerIcon = playerIcon;
        this.playerRole = playerRole;
        this.value = value;
        this.points = points;
        this.offerValue = offerValue;
    }


}
