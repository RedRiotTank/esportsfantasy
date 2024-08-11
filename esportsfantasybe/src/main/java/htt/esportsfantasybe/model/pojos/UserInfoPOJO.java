package htt.esportsfantasybe.model.pojos;

import lombok.Data;

import java.util.UUID;

@Data
public class UserInfoPOJO {
    private UUID uuid;
    private String mail;
    private String username;
    private boolean admin;
    private String icon;
    private String password;

    public UserInfoPOJO(UUID uuid, String mail, String Username, boolean admin, String icon, String password) {
        this.uuid = uuid;
        this.mail = mail;
        this.username = Username;
        this.admin = admin;
        this.icon = icon;
        this.password = password;
    }
}
