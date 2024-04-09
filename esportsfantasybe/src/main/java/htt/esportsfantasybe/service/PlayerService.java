package htt.esportsfantasybe.service;

import com.google.gson.JsonArray;
import htt.esportsfantasybe.DTO.PlayerDTO;
import htt.esportsfantasybe.DTO.RealLeagueDTO;
import htt.esportsfantasybe.model.RealLeague;
import htt.esportsfantasybe.repository.PlayerRepository;
import htt.esportsfantasybe.service.apicaller.LolApiCaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    private LolApiCaller lolApiCaller = new LolApiCaller();

    private final RealLeagueService realLeagueService;

    @Autowired
    public PlayerService(RealLeagueService realLeagueService) {
        this.realLeagueService = realLeagueService;
    }

    public void updatePlayers() {
        //List<RealLeagueDTO> leaguesDB =  realLeagueService.getRLeaguesDB();

        //leaguesDB.forEach(league ->{
            //JsonArray test = lolApiCaller.getMatchSchedule(league.getOverviewpage());
            //System.out.println(test);




       // });

    }




}
