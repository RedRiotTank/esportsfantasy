package htt.esportsfantasybe;

import htt.esportsfantasybe.service.RealLeagueService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main class for the Spring Boot application.
 * @author Alberto Plaza Montes
 */
@SpringBootApplication
@EnableScheduling
public class EsportsfantasybeApplication {

    private final RealLeagueService realLeagueService;

    @Autowired
    public EsportsfantasybeApplication(RealLeagueService realLeagueService) {
        this.realLeagueService = realLeagueService;
    }

    public static void main(String[] args) {
        SpringApplication.run(EsportsfantasybeApplication.class, args);
        System.out.println("eSportsFantasy backend is running");
    }

    @PostConstruct
    public void initialize() {
        //realLeagueService.updateLeagues();
    }
}

