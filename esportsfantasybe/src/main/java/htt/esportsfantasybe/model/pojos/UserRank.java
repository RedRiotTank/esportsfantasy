package htt.esportsfantasybe.model.pojos;

import lombok.Data;

import java.util.UUID;

@Data
public class UserRank {
    private UUID userUuid;
    private String username;
    private String icon;
    private int points;

    public UserRank(UUID userUuid, String username, String icon) {
        this.userUuid = userUuid;
        this.icon = icon;
        this.username = username;
        this.points = 0;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public int compareTo(UserRank other) {
        return Integer.compare(other.points, this.points);
    }
}
