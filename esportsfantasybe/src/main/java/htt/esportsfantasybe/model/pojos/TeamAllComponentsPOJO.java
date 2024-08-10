package htt.esportsfantasybe.model.pojos;

import lombok.Data;

import java.util.HashMap;
import java.util.Set;

@Data
public class TeamAllComponentsPOJO {
    Set<PlayerTeamInfoPOJO> teamInfo;
    HashMap<String, String> resourcesPlayerIcons;
    HashMap<String, String> resourcesTeamIcons;

    public TeamAllComponentsPOJO(Set<PlayerTeamInfoPOJO> teamInfo, HashMap<String, String> resourcesPlayerIcons, HashMap<String, String> resourcesTeamIcons) {
        this.teamInfo = teamInfo;
        this.resourcesPlayerIcons = resourcesPlayerIcons;
        this.resourcesTeamIcons = resourcesTeamIcons;
    }
}
