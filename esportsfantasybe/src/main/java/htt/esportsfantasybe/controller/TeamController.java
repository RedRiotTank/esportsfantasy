package htt.esportsfantasybe.controller;

import com.google.gson.JsonObject;
import htt.esportsfantasybe.Utils;
import htt.esportsfantasybe.model.pojos.PlayerLeaguePOJO;
import htt.esportsfantasybe.responses.koresponses;
import htt.esportsfantasybe.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/Team")
public class TeamController {

    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @CrossOrigin
    @PostMapping(value ="/getPlayerTeamIcon", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPlayerTeamIcon(@RequestBody PlayerLeaguePOJO playerLeaguePOJO){
        try{
            byte[] image = teamService.getPlayerTeamIcon(playerLeaguePOJO.getPlayeruuid(), playerLeaguePOJO.getLeagueuuid());

            return new ResponseEntity<>(image, Utils.getImageHeaders(image), HttpStatus.OK);
        } catch (Exception e){
            return koresponses.generateKO(e.getMessage());
        }
    }


}
