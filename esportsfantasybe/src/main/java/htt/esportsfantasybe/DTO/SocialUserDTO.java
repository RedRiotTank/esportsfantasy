package htt.esportsfantasybe.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SocialUserDTO {
    private String mail;
    private String idToken;
    private String name;

    public SocialUserDTO(String mail, String idToken, String name) {
        this.mail = mail;
        this.idToken = idToken;
        this.name = name;
    }

}
