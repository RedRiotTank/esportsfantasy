package htt.esportsfantasybe.controller;

import htt.esportsfantasybe.DTO.RealLeagueDTO;
import htt.esportsfantasybe.DTO.UserDTO;
import htt.esportsfantasybe.Utils;
import htt.esportsfantasybe.model.pojos.CurrentJourInfoPOJO;
import htt.esportsfantasybe.responses.koresponses;
import htt.esportsfantasybe.responses.okresponses;
import htt.esportsfantasybe.service.RealLeagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/realLeague")
public class RealLeagueController {

    private final RealLeagueService realLeagueService;

    @Autowired
    public RealLeagueController(RealLeagueService realLeagueService) {
        this.realLeagueService = realLeagueService;
    }

    @CrossOrigin
    @PostMapping(value ="/getGameRLeagues", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getGameRLeagues(@RequestBody String game) {
        try {
            Set<RealLeagueDTO> leagues = realLeagueService.getGameRLeaguesDB(game);

            return okresponses.getGameLeagues(leagues);
        } catch(Exception e) {
            return koresponses.generateKO(e.getMessage());
        }
    }

    @CrossOrigin
    @PostMapping(value ="/getRLeagueIcon", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRLeagueIcon(@RequestBody String uuid){
        try{
            byte[] image = realLeagueService.getRLeagueIcon(uuid);

            return new ResponseEntity<>(image, Utils.getImageHeaders(image), HttpStatus.OK);
        } catch (Exception e){
            return koresponses.generateKO(e.getMessage());
        }
    }

    @CrossOrigin
    @PostMapping(value ="/getRLeagueTotalJours", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRLeagueTotalJours(@RequestBody String uuid){
        try{
            int totalJours = realLeagueService.getRLeagueTotalJours(uuid);

            return okresponses.getRLeagueTotalJours(totalJours);
        } catch (Exception e){
            return koresponses.generateKO(e.getMessage());
        }
    }

    @CrossOrigin
    @PostMapping(value ="/getRLeagueCurrentJour", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRLeagueCurrentJour(@RequestBody String uuid){
        try{
            CurrentJourInfoPOJO currentJourInfoPOJO = realLeagueService.getRLeagueCurrentJour(uuid);

            return okresponses.getRLeagueCurrentJour(currentJourInfoPOJO.getJour(), currentJourInfoPOJO.isStarted());
        } catch (Exception e){
            return koresponses.generateKO(e.getMessage());
        }
    }
}
