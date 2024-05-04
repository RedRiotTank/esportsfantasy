package htt.esportsfantasybe.controller;

import com.google.gson.JsonObject;
import htt.esportsfantasybe.DTO.UserDTO;
import htt.esportsfantasybe.Utils;
import htt.esportsfantasybe.model.pojos.JoinLeaguePOJO;
import htt.esportsfantasybe.model.pojos.PlayerInfoPOJO;
import htt.esportsfantasybe.model.pojos.UserLeagueInfoPOJO;
import htt.esportsfantasybe.responses.koresponses;
import htt.esportsfantasybe.responses.okresponses;
import htt.esportsfantasybe.service.LeagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/League")
public class LeagueController {
    private final LeagueService leagueService;

    @Autowired
    public LeagueController(LeagueService leagueService) {
        this.leagueService = leagueService;
    }


    @CrossOrigin
    @PostMapping(value ="/getLeagueIcon", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRLeagueIcon(@RequestBody String uuid){
        try{
            byte[] image = leagueService.getLeagueIcon(uuid);

            return new ResponseEntity<>(image, Utils.getImageHeaders(image), HttpStatus.OK);
        } catch (Exception e){
            return koresponses.generateKO(e.getMessage());
        }
    }

    @CrossOrigin
    @PostMapping(value ="/getUserLeagues", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserLeagues(@RequestBody String mail) {
        try {
            List<UserLeagueInfoPOJO> leagues = leagueService.getUserLeagues(mail);

            return okresponses.getUserLeagues(leagues);
        } catch(Exception e) {
            return koresponses.generateKO(e.getMessage());
        }
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

    @CrossOrigin
    @PostMapping(value ="/getMarketPlayers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMarketPlayers(@RequestBody String leagueUUID) {
        try {
            Set<PlayerInfoPOJO> players = leagueService.getMarketPlayersInfo(UUID.fromString(leagueUUID));

            return okresponses.getMarketPlayers(players);
        } catch(Exception e) {
            return koresponses.generateKO(e.getMessage());
        }
    }






}
