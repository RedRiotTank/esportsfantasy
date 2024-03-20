package htt.esportsfantasybe.DTO;

import htt.esportsfantasybe.service.UserService;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class UserDTO {
    private String mail;
    private String pass;
    private String username;
    private boolean admin;


    public UserDTO(String mail, String pass) {
        this.mail = mail;
        this.pass = pass;
        this.username = UserService.generaetUsername();
        this.admin = false;
    }

    public UserDTO(SocialUserDTO socialUserDTO){
        this.mail = socialUserDTO.getMail();
        this.pass = null;
        this.username = socialUserDTO.getName();
        this.admin = false;
    }

}
