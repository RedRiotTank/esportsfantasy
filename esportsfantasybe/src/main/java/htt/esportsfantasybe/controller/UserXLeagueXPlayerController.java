package htt.esportsfantasybe.controller;

import htt.esportsfantasybe.DTO.UserDTO;
import htt.esportsfantasybe.model.pojos.PlayerTeamInfoPOJO;
import htt.esportsfantasybe.model.pojos.TeamAllComponentsPOJO;
import htt.esportsfantasybe.model.pojos.UserLeaguePOJO;
import htt.esportsfantasybe.model.pojos.UserLeaguePlayerPOJO;
import htt.esportsfantasybe.responses.koresponses;
import htt.esportsfantasybe.responses.okresponses;
import htt.esportsfantasybe.service.complexservices.UserXLeagueXPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/UserXLeagueXPlayer")
public class UserXLeagueXPlayerController {

    private final UserXLeagueXPlayerService userXLeagueXPlayerService;

    @Autowired
    public UserXLeagueXPlayerController(UserXLeagueXPlayerService userXLeagueXPlayerService) {
        this.userXLeagueXPlayerService = userXLeagueXPlayerService;
    }

    @CrossOrigin
    @PostMapping(value ="/TeamInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> TeamInfo(@RequestBody UserLeaguePOJO userLeaguePOJO){
        try{
            TeamAllComponentsPOJO teamAllComponentsPOJO =  userXLeagueXPlayerService.getUserXLeagueTeam(userLeaguePOJO.getUseruuid(), userLeaguePOJO.getLeagueuuid());

            return okresponses.teamInfo(teamAllComponentsPOJO);
        } catch (Exception e){
            return koresponses.generateKO(e.getMessage());
        }
    }


    @CrossOrigin
    @PostMapping(value ="/SetAligned", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> TeamInfo(@RequestBody UserLeaguePlayerPOJO userLeaguePlayerPOJO){
        try{
            userXLeagueXPlayerService.setAligned(userLeaguePlayerPOJO.getUseruuid(), userLeaguePlayerPOJO.getLeagueuuid(), userLeaguePlayerPOJO.getPlayeruuid(), userLeaguePlayerPOJO.getAligned());

            return okresponses.setAligned();
        } catch (Exception e){
            return koresponses.generateKO(e.getMessage());
        }
    }

}
