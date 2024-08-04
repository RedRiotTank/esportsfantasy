package htt.esportsfantasybe.controller;

import htt.esportsfantasybe.DTO.UserDTO;
import htt.esportsfantasybe.Utils;
import htt.esportsfantasybe.model.pojos.PlayerInfoPOJO;
import htt.esportsfantasybe.model.pojos.PlayerLeaguePOJO;
import htt.esportsfantasybe.responses.koresponses;
import htt.esportsfantasybe.responses.okresponses;
import htt.esportsfantasybe.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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


    @CrossOrigin
    @PostMapping(value ="/getPlayerIcon", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPlayerIcon(@RequestBody String uuid){
        try{
            byte[] image = playerService.getPlayerIcon(uuid);

            return new ResponseEntity<>(image, Utils.getImageHeaders(image), HttpStatus.OK);
        } catch (Exception e){
            return koresponses.generateKO(e.getMessage());
        }
    }

    @CrossOrigin
    @PostMapping(value ="/getPlayerInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPlayerInfo(@RequestBody PlayerLeaguePOJO playerLeaguePOJO){
        try{
             PlayerInfoPOJO pInfo = playerService.getPlayerInfo(playerLeaguePOJO);

            return okresponses.getPlayerInfo(pInfo);
        } catch (Exception e){
            return koresponses.generateKO(e.getMessage());
        }
    }

}
