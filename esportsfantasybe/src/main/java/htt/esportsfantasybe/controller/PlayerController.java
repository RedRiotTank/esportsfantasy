package htt.esportsfantasybe.controller;

import htt.esportsfantasybe.DTO.UserDTO;
import htt.esportsfantasybe.responses.koresponses;
import htt.esportsfantasybe.responses.okresponses;
import htt.esportsfantasybe.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/Player")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @CrossOrigin
    @PostMapping(value ="/test", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> testendpoint(@RequestBody UserDTO newUserDTO) {
        try {
            //userService.signup(newUserDTO);

            return okresponses.createdNewUser();
        } catch(Exception e) {
            return koresponses.generateKO(e.getMessage());
        }
    }
}
