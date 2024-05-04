package htt.esportsfantasybe.model.complexentities;

import htt.esportsfantasybe.model.complexkeysmodels.UserXLeagueId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "userxleague")
public class UserXLeague {

    @EmbeddedId
    private UserXLeagueId id;

    @Column(name = "isadmin")
    boolean isAdmin;

    private int money;

    public UserXLeague(UserXLeagueId id, boolean isAdmin, int money) {
        this.id = id;
        this.isAdmin = isAdmin;
        this.money = money;
    }

    public UserXLeague() {

    }
}
