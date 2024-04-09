package htt.esportsfantasybe.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerDTO {

    private String username;
    private String fullname;
    private String role;
    private float value;

    public PlayerDTO(String username, String fullname, String role, float value) {
        this.username = username;
        this.fullname = fullname;
        this.role = role;
        this.value = value;
    }


}
