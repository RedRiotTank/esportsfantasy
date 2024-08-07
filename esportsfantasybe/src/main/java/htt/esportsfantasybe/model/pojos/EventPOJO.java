package htt.esportsfantasybe.model.pojos;

import lombok.Getter;
import lombok.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
public class EventPOJO {
    private String team1;
    private String team2;
    private String team1Score;
    private String team2Score;
    private String jour;
    private Date dateTime;
    private String matchId;
    private String mvp;


    public EventPOJO(String team1, String team2, String jour,String team1Score, String team2Score ,String dateTime_UTC, String matchId, String mvp) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        this.team1 = team1;
        this.team2 = team2;
        this.jour = jour;
        this.team1Score = team1Score;
        this.team2Score = team2Score;
        this.matchId = matchId;
        this.mvp = mvp;

        try {
            formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            this.dateTime = formatter.parse(dateTime_UTC);
        } catch (Exception e) {
            e.printStackTrace();
            dateTime = null;
        }


    }
}
