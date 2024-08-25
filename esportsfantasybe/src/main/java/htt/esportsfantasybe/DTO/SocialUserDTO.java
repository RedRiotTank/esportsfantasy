package htt.esportsfantasybe.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Data Transfer Object for SocialUser.
 * @author Alberto Plaza Montes.
 */
@Setter
@Getter
public class SocialUserDTO {
    private String mail;
    private String idToken;
    private String name;
    private UUID uuid;
    private String iconLink;

    SocialUserDTO() {
    }

    /**
     * Constructor for SocialUserDTO.
     * @param mail mail of the user.
     * @param idToken idToken of the user (if social login).
     * @param name name of the user.
     */
    public SocialUserDTO(String mail, String idToken, String name, String iconLink) {
        this.mail = mail;
        this.idToken = idToken;
        this.name = name;
        this.iconLink = iconLink;
    }

    /**
     * Constructor for SocialUserDTO.
     * @param mail mail of the user.
     * @param name name of the user.
     */
    public SocialUserDTO(String mail, String name) {
        this.mail = mail;
        this.idToken = null;
        this.name = name;
    }

}
