package htt.esportsfantasybe.controller;

import com.google.gson.JsonObject;
import htt.esportsfantasybe.DTO.UserDTO;
import htt.esportsfantasybe.model.pojos.JoinLeaguePOJO;
import htt.esportsfantasybe.responses.koresponses;
import htt.esportsfantasybe.responses.okresponses;
import htt.esportsfantasybe.service.LeagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/League")
public class LeagueController {
    private final LeagueService leagueService;

    @Autowired
    public LeagueController(LeagueService leagueService) {
        this.leagueService = leagueService;
    }


    @CrossOrigin
    @PostMapping(value ="/joinLeague", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> joinLeague(@RequestBody JoinLeaguePOJO joinLeaguePOJO) {
        try {
            leagueService.joinLeague(joinLeaguePOJO);

            return okresponses.joinLeague();
        } catch(Exception e) {
            return koresponses.generateKO(e.getMessage());
        }
    }
}
