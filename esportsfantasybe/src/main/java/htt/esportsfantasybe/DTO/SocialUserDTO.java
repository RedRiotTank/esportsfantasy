package htt.esportsfantasybe.DTO;

import lombok.Getter;
import lombok.Setter;

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

    /**
     * Constructor for SocialUserDTO.
     * @param mail mail of the user.
     * @param idToken idToken of the user (if social login).
     * @param name name of the user.
     */
    public SocialUserDTO(String mail, String idToken, String name) {
        this.mail = mail;
        this.idToken = idToken;
        this.name = name;
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
