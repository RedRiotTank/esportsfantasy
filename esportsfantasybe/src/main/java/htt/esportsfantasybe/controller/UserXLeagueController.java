package htt.esportsfantasybe.controller;

import htt.esportsfantasybe.Utils;
import htt.esportsfantasybe.model.pojos.PlayerLeaguePOJO;
import htt.esportsfantasybe.responses.koresponses;
import htt.esportsfantasybe.responses.okresponses;
import htt.esportsfantasybe.service.complexservices.UserXLeagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/api/userxleague")
public class UserXLeagueController {

    private final UserXLeagueService userXLeagueService;

    @Autowired
    public UserXLeagueController(UserXLeagueService userXLeagueService) {
        this.userXLeagueService = userXLeagueService;
    }

    @CrossOrigin
    @PostMapping(value ="/getUserXLeagueMoney", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserXLeagueMoney(@RequestBody PlayerLeaguePOJO params){
        try{
             int money = userXLeagueService.getUserXLeagueMoney(UUID.fromString(params.getPlayeruuid()), UUID.fromString(params.getLeagueuuid()));

            return okresponses.getUserXLeagueMoney(money);
        } catch (Exception e){
            return koresponses.generateKO(e.getMessage());
        }
    }

}
