package htt.esportsfantasybe.DTO;

import htt.esportsfantasybe.model.User;
import htt.esportsfantasybe.service.UserService;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Data Transfer Object for User.
 * @author Alberto Plaza Montes
 */
@Setter
@Getter
public class UserDTO {
    private UUID uuid;
    private String mail;
    private String pass;
    private String username;
    private boolean admin;

    /**
     * Constructor for UserDTO.
     * @param mail mail of the user.
     * @param pass password of the user.
     */
    public UserDTO(String mail, String pass) {
        this.mail = mail;
        this.pass = pass;
        this.username = UserService.generaetUsername();
        this.admin = false;
    }

    /**
     * Constructor for UserDTO.
     * @param socialUserDTO socialUserDTO to convert to UserDTO.
     */
    public UserDTO(SocialUserDTO socialUserDTO){
        this.mail = socialUserDTO.getMail();
        this.pass = null;
        this.username = socialUserDTO.getName();
        this.admin = false;
    }

    public UserDTO(User user) {
        this.uuid = user.getUuid();
        this.mail = user.getMail();
        this.pass = user.getPass();
        this.username = user.getUsername();
        this.admin = user.isAdmin();
    }
}
