package htt.esportsfantasybe.DTO;

import htt.esportsfantasybe.model.Player;
import htt.esportsfantasybe.model.RealLeague;
import htt.esportsfantasybe.model.Team;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class PlayerDTO {

    private UUID uuid;

    private String username;
    private String fullname;
    private String image;
    private String role;
    private float value;

    private Set<Team> teams;

    public PlayerDTO(UUID uuid, String username,String image, String fullname, String role, float value) {
        this.uuid = uuid;
        this.username = username;
        this.image = image;
        this.fullname = fullname;
        this.role = role;
        this.value = value;
    }


    public PlayerDTO(String username,String image, String fullname, String role, float value) {
        this(null,username,image,fullname,role,value);
    }

    public PlayerDTO(Player player){
        this.uuid = player.getUuid();
        this.username = player.getUsername();
        this.fullname = player.getFullname();
        this.image = player.getImage();
        this.role = player.getRole();
        this.value = player.getValue();
    }


}
