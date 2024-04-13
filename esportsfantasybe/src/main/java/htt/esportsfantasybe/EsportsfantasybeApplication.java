package htt.esportsfantasybe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import htt.esportsfantasybe.DTO.PlayerDTO;
import htt.esportsfantasybe.DTO.RealLeagueDTO;
import htt.esportsfantasybe.DTO.TeamDTO;
import htt.esportsfantasybe.model.Player;
import htt.esportsfantasybe.model.Team;
import htt.esportsfantasybe.model.complexentities.TeamXrLeague;
import htt.esportsfantasybe.service.PlayerService;
import htt.esportsfantasybe.service.RealLeagueService;
import htt.esportsfantasybe.service.TeamService;
import htt.esportsfantasybe.service.apicaller.CounterApiCaller;
import htt.esportsfantasybe.service.apicaller.LolApiCaller;
import htt.esportsfantasybe.service.complexservices.TeamXrLeagueService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Main class for the Spring Boot application.
 * @author Alberto Plaza Montes
 */
@SpringBootApplication
@EnableScheduling
public class EsportsfantasybeApplication {

    private final RealLeagueService realLeagueService;
    private final PlayerService playerService;
    private final TeamService teamService;

    private final TeamXrLeagueService teamXrLeagueService ;

    CounterApiCaller counterApiCaller = new CounterApiCaller();
    LolApiCaller lolApiCaller = new LolApiCaller();

    @Autowired
    public EsportsfantasybeApplication(RealLeagueService realLeagueService, PlayerService playerService, TeamService teamService, TeamXrLeagueService teamXrLeagueService) {
        this.realLeagueService = realLeagueService;
        this.playerService = playerService;
        this.teamService = teamService;
        this.teamXrLeagueService = teamXrLeagueService;
    }

    public static void main(String[] args) {
        SpringApplication.run(EsportsfantasybeApplication.class, args);
        System.out.println("eSportsFantasy backend is running");
    }

    @PostConstruct
    public void initialize() throws IOException {
        realLeagueService.updateLeagues();
        //lolApiCaller.downloadLeagueImage("LEC/2024 Season/Spring");
        //Set<PlayerDTO> players =  lolApiCaller.getTeamPlayers(teamService.getTeamDataDB());


        //teamService.updateTeams();

        //List<TeamXrLeague> all=teamXrLeagueService.getall();

        /*
        realLeagueService.getRLeaguesDB().forEach(league ->{
            System.out.println(" ==== Liga: " + league.getEvent() + " ====");
            List<TeamDTO> teams = teamService.getLeagueTeams(league.getUuid());
            teams.forEach(team -> {
                System.out.println("    " + team.getName());
            });
        });
        */
    }
}

