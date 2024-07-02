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
        //realLeagueService.initializeLeagues();
        // ----------- END UPDATE ----------- //


    }
}

