package htt.esportsfantasybe;

import htt.esportsfantasybe.service.RealLeagueService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;

/**
 * Main class for the Spring Boot application.
 * @author Alberto Plaza Montes
 */
@SpringBootApplication
@EnableScheduling
public class EsportsfantasybeApplication {



    @Autowired
    private RealLeagueService realLeagueService;


    public static void main(String[] args) {
        SpringApplication.run(EsportsfantasybeApplication.class, args);
        System.out.println("eSportsFantasy backend is running");


    }

    @PostConstruct
    public void initialize() throws IOException {

        // ----------- FULL UPDATE ----------- //
        realLeagueService.initializeLeagues();
        // ----------- END UPDATE ----------- //

/*
        System.out.printf("a");
        userService.getAllUsers().forEach(user -> {
            List<LeagueDTO> leagues = userService.getUserLeagues(user.getUuid());
            System.out.println(user.getMail() + " has the following leagues: ");
            System.out.println(leagues);
        });

 */
        /*
        realLeagueService.getRLeaguesDB().forEach(league ->{
            realLeagueService.updateLeagueJournal(league.getUuid());

        });
        */


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

