package htt.esportsfantasybe.controller;

import htt.esportsfantasybe.DTO.UserDTO;
import htt.esportsfantasybe.model.pojos.PlayerTeamInfoPOJO;
import htt.esportsfantasybe.model.pojos.UserLeaguePOJO;
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
            System.out.println("GETTING TEAM INFO");
            Set<PlayerTeamInfoPOJO> teamInfo =  userXLeagueXPlayerService.getUserXLeagueTeam(userLeaguePOJO.getUseruuid(), userLeaguePOJO.getLeagueuuid());

            return okresponses.teamInfo(teamInfo);
        } catch (Exception e){
            return koresponses.generateKO(e.getMessage());
        }
    }
}
