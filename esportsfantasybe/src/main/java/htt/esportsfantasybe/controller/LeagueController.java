package htt.esportsfantasybe.controller;

import htt.esportsfantasybe.DTO.UserDTO;
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
    @PostMapping(value ="/test", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> testendpoint(@RequestBody UserDTO newUserDTO) {
        try {

            System.out.println("TESTING");

            return okresponses.createdNewUser();
        } catch(Exception e) {
            return koresponses.createdNewUser(e.getMessage());
        }
    }
}
