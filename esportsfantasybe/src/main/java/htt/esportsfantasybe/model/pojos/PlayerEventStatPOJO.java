package htt.esportsfantasybe.model.pojos;

import lombok.Getter;

@Getter
public class PlayerEventStatPOJO {

    private String name;
    private String champion;
    private int kills;
    private int deaths;
    private int assists;
    private int cs;
    private int gold;
    private int damageToChampions;
    private int visionScore;
    private int teamKills;

    private int teamGold;

    private boolean win;
    private String role;

    public PlayerEventStatPOJO(String name, String champion, int kills, int deaths, int assists, int cs, int gold, int damageToChampions, int visionScore, int teamKills, int teamGold, boolean win, String role) {
        this.name = name;
        this.champion = champion;
        this.kills = kills;
        this.deaths = deaths;
        this.assists = assists;
        this.cs = cs;
        this.gold = gold;
        this.damageToChampions = damageToChampions;
        this.visionScore = visionScore;
        this.teamKills = teamKills;
        this.teamGold = teamGold;
        this.win = win;
        this.role = role;
    }
}
