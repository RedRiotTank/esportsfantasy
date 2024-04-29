package htt.esportsfantasybe.controller;

import htt.esportsfantasybe.DTO.UserDTO;
import htt.esportsfantasybe.Utils;
import htt.esportsfantasybe.responses.koresponses;
import htt.esportsfantasybe.responses.okresponses;
import htt.esportsfantasybe.service.GamesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/games")
public class GamesController {
    private final GamesService gamesService;

    @Autowired
    public GamesController(GamesService gamesService) {
        this.gamesService = gamesService;
    }

    @CrossOrigin
    @PostMapping(value ="/getGames", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getGames() {
        try {
            return okresponses.getGames(gamesService.getGames());
        } catch (Exception e) {
            return koresponses.generateKO(e.getMessage());
        }
    }

    @CrossOrigin
    @PostMapping(value ="/getGameIcon", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getGameIcon(@RequestBody String game){
        try{
            byte[] image = gamesService.getGameIcon(game);

            return new ResponseEntity<>(image, Utils.getImageHeaders(image), HttpStatus.OK);
        } catch (Exception e){
            return koresponses.generateKO(e.getMessage());
        }
    }
}
