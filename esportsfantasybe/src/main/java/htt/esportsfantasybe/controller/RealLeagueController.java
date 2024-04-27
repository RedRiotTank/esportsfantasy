package htt.esportsfantasybe.controller;

import htt.esportsfantasybe.DTO.RealLeagueDTO;
import htt.esportsfantasybe.DTO.UserDTO;
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
            return koresponses.createdNewUser(e.getMessage());
        }
    }

    @CrossOrigin
    @PostMapping(value ="/getRLeagueIcon", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRLeagueIcon(@RequestBody String uuid){
        try{
            System.out.println("AAA");
            byte[] image = realLeagueService.getRLeagueIcon(uuid);


            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG); // Cambiar según el tipo de imagen
            headers.setContentLength(image.length);

            return new ResponseEntity<>(image, headers, HttpStatus.OK);
        } catch (Exception e){
            return koresponses.googleLogin(e.getMessage());
        }
    }





    @CrossOrigin
    @PostMapping(value ="/test", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> testendpoint(@RequestBody UserDTO newUserDTO) {
        try {
            //userService.signup(newUserDTO);

            return okresponses.createdNewUser();
        } catch(Exception e) {
            return koresponses.createdNewUser(e.getMessage());
        }
    }

}
